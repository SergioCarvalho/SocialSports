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


public class PesquisaEquipas {

	static Vector<Integer> lista_modalidadesequipa = new Vector<Integer>();
	
	public static void iniciaPesquisaEquipas() throws IOException, ParseException{

		PesquisaEquipas pEquipas = new PesquisaEquipas();

		Vector<Integer> lista_idhashtagsequipa = pEquipas.devolveIdsHashtagsEquipas();
				
		for(int i = 0; i!=lista_idhashtagsequipa.size();i++){
			String hashtag_equipa = devolveDescHashEquipa(lista_idhashtagsequipa.elementAt(i));
			int modalidade = lista_modalidadesequipa.elementAt(i);
//			System.out.println("Hashtag de equipa a ser pesquisada: " + hashtag_equipa);
//			System.out.println("Modalidade associada: " + modalidade);
			pEquipas.pesquisaHashtagEquipa(hashtag_equipa, modalidade);
		}
	}

	//devolve a descri��o da hashtag de equipa tendo por base o id_hashequipa
	private static String devolveDescHashEquipa(Integer id_hashequipa) {
		// TODO Auto-generated method stub
		String deschash_equipa = "";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select deschash_equipa from hashequipa where id_hashequipa = '" + id_hashequipa + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				deschash_equipa = rs.getString(1);
			}
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return deschash_equipa;
	}

	//vou buscar os id_hashequipa e id_modalidade da tabela utilizadorhashequipa
	//decidi fazer pesquisas apenas pelas hashtags de interesse actual dos utilizadores, em vez de fazer pesquisas por todas
	//as hashtags existentes na tabela hashequipa, visto permanecerem nessa tabela hashtags que podem ter sido do interesse de
	//algum utilizador mas neste momento n�o ser do interesse de nenhum 
	private Vector<Integer> devolveIdsHashtagsEquipas() {
		// TODO Auto-generated method stub
		Vector<Integer> vector_idhashtags_equipas = new Vector<Integer>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashequipa, id_modalidade from utilizadorhashequipa";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vector_idhashtags_equipas.add(rs.getInt(1));
				lista_modalidadesequipa.add(rs.getInt(2));
			}
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vector_idhashtags_equipas;
	}

	public void pesquisaHashtagEquipa(String hashtag_equipa, int modalidade)throws IOException, ParseException{
		
		//numero de p�ginas a procurar (cada p�gina ter� x tweets por p�gina - query.rpp)
		//2 p�ginas com 75 tweets por p�gina, vou procurar 150 novos tweets por itera��o
		int num_paginas = 2;
		TwitterFactory twitterFactory = new TwitterFactory();
		Twitter twitter = twitterFactory.getInstance();
		
		/*ANTERIOR IMPLEMENTA��O
		//vari�vel para verificar a data do tweet para caso seja mais antiga que o ultimo
		//tweet guardado sai do ciclo e n�o verifica todas as restantes mais antigas
		String ultima_data = FerramentasData.devolveUltimaDataPorHashtag(hashtag_equipa, FerramentasData.devolveDataHojeString());
		ANTERIOR IMPLEMENTA��O*/
		
		//vector que fica com as mensagens inseridas no �ndice para comparar duplicados (hashtag a hashtag)
		Vector<String> vec_mensagens = new Vector<String>();
		
		//i vai come�ar a 1 pq a 1� p�gina a apresentar � a 1
		for (int i = 1; i <= num_paginas; i++) {
			
			//page define o n�mero da p�gina a retornar (come�a em 1)
			Query query = new Query(hashtag_equipa).page(i);
			//n�mero de tweets por p�gina
			query.rpp(75); 						

			QueryResult resultadoProcura = null;
			
			try {
				resultadoProcura = twitter.search(query);
				List<Tweet> listaTweets = resultadoProcura.getTweets();
				Iterator<Tweet> itTweet = listaTweets.iterator();				

				//while_loop:
					while (itTweet.hasNext()) {
						Tweet tweet = itTweet.next();
						
						/*ANTERIOR IMPLEMENTA��O
						//1� verifica se o tweet pertence ao pr�prio dia, se n�o sai do ciclo
						//porque todos os restantes s�o mais antigos
						if(!FerramentasData.pertenceDiaHoje(tweet.getCreatedAt())){
							break while_loop;
						}
						//2� compara com a data do ultimo tweet guardado para esta hashtag: se for inferior sai do ciclo
						//e n�o adiciona este tweet ao �ndice nem todos os restantes porque s�o mais antigos
						if(ultima_data != null && Integer.parseInt(ultima_data)>Integer.parseInt(FerramentasData.devolveDataHoraMinuto(FerramentasData.devolveDataTwitterString(tweet.getCreatedAt())))){
							break while_loop;
						}
						ANTERIOR IMPLEMENTA��O*/
						//else
							if(tweet.getIsoLanguageCode() != null && (tweet.getIsoLanguageCode().equals("en") || 
									tweet.getIsoLanguageCode().equals("pt"))){
							String utilizador = tweet.getFromUser();
							String imagem = tweet.getProfileImageUrl();
							String mensagem = tweet.getText();
							String data_criacao = FerramentasData.devolveDataTwitterString(tweet.getCreatedAt());
							String lingua = tweet.getIsoLanguageCode();
							String fonte = "Twitter";
							
							MensagemEquipa.recebeMensagem(hashtag_equipa, modalidade, utilizador, imagem, mensagem, 
									data_criacao, lingua, fonte, vec_mensagens);
						}
					}
			}

			catch (TwitterException e) {
				e.printStackTrace();
				System.out.println("Erro no PesquisaEquipas - pesquisaHashtagsEquipas");
			}

		}
	}
}