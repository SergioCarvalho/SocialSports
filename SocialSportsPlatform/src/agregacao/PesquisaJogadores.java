package agregacao;
import categorizacao.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.lucene.queryParser.ParseException;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class PesquisaJogadores {

	static Vector<Integer> lista_modalidadesjogador = new Vector<Integer>();

	public static void iniciaPesquisaJogadores() throws IOException, ParseException{
		PesquisaJogadores pJogadores = new PesquisaJogadores();

		Vector<Integer> lista_hashtagsjogadores = pJogadores.devolveHashtagsJogadores();

		for(int i = 0; i!=lista_hashtagsjogadores.size();i++){
			String hashtag_jogador = devolveDescHashJogador(lista_hashtagsjogadores.elementAt(i));
			int modalidade = lista_modalidadesjogador.elementAt(i);
			//			System.out.println("Hashtag do jogador a ser pesquisado: " + hashtag_jogador);
			//			System.out.println("Modalidade associada: " + modalidade);
			pJogadores.pesquisaHashtagJogador(hashtag_jogador, modalidade);
		}
	}

	private static String devolveDescHashJogador(Integer id_hashjogador) {
		// TODO Auto-generated method stub
		String deschash_jogador = "";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select deschash_jogador from hashjogador where id_hashjogador = '" + id_hashjogador + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				deschash_jogador = rs.getString(1);
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return deschash_jogador;
	}

	private Vector<Integer> devolveHashtagsJogadores() {
		// TODO Auto-generated method stub
		Vector<Integer> vector_idhashtags_jogadores = new Vector<Integer>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashjogador, id_modalidade from utilizadorhashjogador";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vector_idhashtags_jogadores.add(rs.getInt(1));
				lista_modalidadesjogador.add(rs.getInt(2));
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vector_idhashtags_jogadores;
	}

	public void pesquisaHashtagJogador(String hashtag_jogador, int modalidade)throws IOException, ParseException{
		//numero de p�ginas a procurar (cada p�gina ter� x tweets por p�gina - query.rpp)
		int num_paginas = 2; //ATEN��O AQUI MUDEI DE 10 PARA 2 PARA FAZER MENOS CHAMADAS � API
		TwitterFactory twitterFactory = new TwitterFactory();
		Twitter twitter = twitterFactory.getInstance();

		/*anterior implementacao
		//vari�vel para verificar a data do tweet para caso seja mais antiga que o ultimo
		//tweet guardado sai do ciclo e n�o verifica todas as restantes mais antigas
		String ultima_data = FerramentasData.devolveUltimaDataPorHashtag(hashtag_jogador, FerramentasData.devolveDataHojeString());
		anterior implementacao*/
		
		//vector que fica com as mensagens inseridas no �ndice para comparar duplicados (hashtag a hashtag)
		Vector<String> vec_mensagens = new Vector<String>();
		
		//i vai come�ar a 1 pq a 1� p�gina a apresentar � a 1
		for (int i = 1; i <= num_paginas; i++) {
			//
			//page define o n�mero da p�gina a retornar (come�a em 1)
			Query query = new Query(hashtag_jogador).page(i);
			//n�mero de tweets por p�gina
			query.rpp(75); //ATEN��O AQUI MUDEI DE 15 PARA 75 PARA FAZER MENOS CHAMADAS � API 						

			QueryResult resultadoProcura = null;

			try {
				resultadoProcura = twitter.search(query);
				List<Tweet> listaTweets = resultadoProcura.getTweets();
				Iterator<Tweet> itTweet = listaTweets.iterator();

//				while_loop:
					while (itTweet.hasNext()) {
						Tweet tweet = itTweet.next();
						
						/*anterior implementacao
						//1� verifica se o tweet pertence ao pr�prio dia, se n�o sai do ciclo
						//porque todos os restantes s�o mais antigos
						if(!FerramentasData.pertenceDiaHoje(tweet.getCreatedAt())){
							break while_loop;
						}
						
						
						//2� compara com a data do ultimo tweet guardado para esta hashtag: for superior sai do ciclo
						//n�o adiciona este tweet ao �ndice nem todos os restantes porque s�o mais antigos
						if(ultima_data != null && Integer.parseInt(ultima_data)>Integer.parseInt(FerramentasData.devolveDataHoraMinuto(FerramentasData.devolveDataTwitterString(tweet.getCreatedAt())))){
							break while_loop;
						}

						anterior implementacao*/
						//else
							if(tweet.getIsoLanguageCode() != null && (tweet.getIsoLanguageCode().equals("en") || 
									tweet.getIsoLanguageCode().equals("pt"))){
								String utilizador = tweet.getFromUser();
								String imagem = tweet.getProfileImageUrl();
								String mensagem = tweet.getText();
								String data_criacao = FerramentasData.devolveDataTwitterString(tweet.getCreatedAt());
								String lingua = tweet.getIsoLanguageCode();
								String fonte = "Twitter";

								MensagemJogador.recebeMensagem(hashtag_jogador, modalidade, utilizador, imagem, mensagem, 
										data_criacao, lingua, fonte, vec_mensagens);
							}
					}
			}

			catch (TwitterException e) {
				e.printStackTrace();
				System.out.println("Erro no PesquisaJogadores - pesquisaHashtagsJogadores");
			}

		}
	}
}
