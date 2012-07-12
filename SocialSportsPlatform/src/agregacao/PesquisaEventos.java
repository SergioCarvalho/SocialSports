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

public class PesquisaEventos {

	static Vector<Integer> lista_modalidadesevento = new Vector<Integer>();

	public static void iniciaPesquisaEventos() throws IOException, ParseException{

		PesquisaEventos pEventos = new PesquisaEventos();

		Vector<Integer> lista_hashtagsevento = pEventos.devolveHashtagsEventos();

		for(int i = 0; i!=lista_hashtagsevento.size();i++){
			String hashtag_evento = devolveDescHashEvento(lista_hashtagsevento.elementAt(i));
			int modalidade = lista_modalidadesevento.elementAt(i);
			//System.out.println("Hashtag do evento a ser pesquisado: " + hashtag_evento);
			//System.out.println("Modalidade associada: " + modalidade);
			pEventos.pesquisaHashtagEvento(hashtag_evento, modalidade);
		}
	}

	private static String devolveDescHashEvento(Integer id_hashevento) {
		// TODO Auto-generated method stub
		String deschash_evento = "";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select deschash_evento from hashevento where id_hashevento = '" + id_hashevento + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				deschash_evento = rs.getString(1);
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return deschash_evento;
	}

	private Vector<Integer> devolveHashtagsEventos() {
		// TODO Auto-generated method stub
		Vector<Integer> vector_idhashtags_eventos = new Vector<Integer>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashevento, id_modalidade from utilizadorhashevento";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vector_idhashtags_eventos.add(rs.getInt(1));
				lista_modalidadesevento.add(rs.getInt(2));
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vector_idhashtags_eventos;
	}

	public void pesquisaHashtagEvento(String hashtag_evento, int modalidade)throws IOException, ParseException{
		//numero de p�ginas a procurar (cada p�gina ter� x tweets por p�gina - query.rpp)
		int num_paginas = 2; //ATEN��O AQUI MUDEI DE 10 PARA 2 PARA FAZER MENOS CHAMADAS � API
		TwitterFactory twitterFactory = new TwitterFactory();
		Twitter twitter = twitterFactory.getInstance();

		/*ANTERIOR IMPLEMENTACAO
		//vari�vel para verificar a data do tweet para caso seja mais antiga que o ultimo
		//tweet guardado sai do ciclo e n�o verifica todas as restantes mais antigas
		String ultima_data = FerramentasData.devolveUltimaDataPorHashtag(hashtag_evento, FerramentasData.devolveDataHojeString());
		ANTERIOR IMPLEMENTACAO*/

		//vector que fica com as mensagens inseridas no �ndice para comparar duplicados (hashtag a hashtag)
		Vector<String> vec_mensagens = new Vector<String>();
		
		//i vai come�ar a 1 pq a 1� p�gina a apresentar � a 1
		for (int i = 1; i <= num_paginas; i++) {
			//
			//page define o n�mero da p�gina a retornar (come�a em 1)
			Query query = new Query(hashtag_evento).page(i);
			//n�mero de tweets por p�gina
			query.rpp(75); //ATEN��O AQUI MUDEI DE 15 PARA 75 PARA FAZER MENOS CHAMADAS � API				

			QueryResult resultadoProcura = null;

			try {
				resultadoProcura = twitter.search(query);
				List<Tweet> listaTweets = resultadoProcura.getTweets();
				Iterator<Tweet> itTweet = listaTweets.iterator();

				//while_loop:
					while (itTweet.hasNext()) {
						Tweet tweet = itTweet.next();
						
						/*ANTERIOR IMPLEMENTACAO
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
						ANTERIOR IMPLEMENTACAO*/
//						else
							if(tweet.getIsoLanguageCode() != null && (tweet.getIsoLanguageCode().equals("en") || 
									tweet.getIsoLanguageCode().equals("pt"))){
								String utilizador = tweet.getFromUser();
								String imagem = tweet.getProfileImageUrl();
								String mensagem = tweet.getText();
								String data_criacao = FerramentasData.devolveDataTwitterString(tweet.getCreatedAt());
								String lingua = tweet.getIsoLanguageCode();
								String fonte = "Twitter";

								MensagemEvento.recebeMensagem(hashtag_evento, modalidade, utilizador, imagem, mensagem,
										data_criacao, lingua, fonte, vec_mensagens);
							}
					}
			}

			catch (TwitterException e) {
				e.printStackTrace();
				System.out.println("Erro no PesquisaEventos - pesquisaHashtagsEventos");
			}

		}
	}
}
