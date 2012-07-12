package agregacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import indexacao.*;

//1� verificar preferencias do utilizador 
//2� de acordo com as preferencias fazer as pesquisas nos �ndices sobre todas as hashtags de interesse
//do utilizador (�ltimos 2 dias talvez)
//3� ordena��o dos resultados em fun��o da sua data
//4� gera��o de ficheiros do tipo json prontos a ser enviados para a app m�vel

public class GereDadosMsgAppM {

	//As hip�teses de preferencia s�o
		//1� Tipo Informa��o: info, geral, geralinfo
		//2� L�ngua: portugues, ingles, portuguesingles
		//3� Fonte Informa��o: twitter, socialsports, socsportstwitter
	
	public static void agregaDadosPreferencia() throws Exception{
		//1� vou buscar os dados de preferencia do utilizador (neste caso usamos sempre o utilizador com id = 1)
		int id_utilizador = 1;
		Vector<String> vec_preferencias = Preferencias.devolvePreferenciasBDUtilizador(id_utilizador);
		String tipo_mensagem = vec_preferencias.get(0);
		String lingua = vec_preferencias.get(1);
		String fonte_info = vec_preferencias.get(2);
		
		Vector<String> vec_hashtags = devolveTodasHashtagsPref();
		
		System.out.println("As prefs: " + tipo_mensagem + ", " + lingua + ", " + fonte_info);
		
		if(tipo_mensagem.equals("geralinfo") && lingua.equals("portuguesingles")&& fonte_info.equals("socsportstwitter")){
			//pesquisa feita sem limita��o das prefer�ncias
			AgregaDadosVisualizacao.agregaTodos(vec_hashtags);
		}

		else if(!tipo_mensagem.equals("geralinfo") && lingua.equals("portuguesingles")&& fonte_info.equals("socsportstwitter")){
			//pesquisa feita em fun��o do tipo de informa��o (apenas geral ou informa��o)
			AgregaDadosVisualizacao.agregaPorTipo(tipo_mensagem, vec_hashtags);
		}
		
		else if(tipo_mensagem.equals("geralinfo") && !lingua.equals("portuguesingles")&& fonte_info.equals("socsportstwitter")){
			//pesquisa feita em fun��o da l�ngua (apenas portugues ou ingles)
			AgregaDadosVisualizacao.agregaPorLingua(lingua, vec_hashtags);
		}
		
		else if(tipo_mensagem.equals("geralinfo") && lingua.equals("portuguesingles")&& !fonte_info.equals("socsportstwitter")){
			//pesquisa feita em fun��o da fonte (apenas twitter ou socialsports)
			AgregaDadosVisualizacao.agregaPorFonte(fonte_info, vec_hashtags);
		}
		
		else if(!tipo_mensagem.equals("geralinfo") && !lingua.equals("portuguesingles")&& fonte_info.equals("socsportstwitter")){
			//pesquisa feita em fun��o do tipo de informacao e lingua
			AgregaDadosVisualizacao.agregaPorTipoLingua(tipo_mensagem, lingua, vec_hashtags);
		}
		
		else if(!tipo_mensagem.equals("geralinfo") && lingua.equals("portuguesingles")&& !fonte_info.equals("socsportstwitter")){
			//pesquisa feita em fun��o do tipo de informacao e fonte
			AgregaDadosVisualizacao.agregaPorTipoFonte(tipo_mensagem, fonte_info, vec_hashtags);
		}
		
		else if(tipo_mensagem.equals("geralinfo") && !lingua.equals("portuguesingles")&& !fonte_info.equals("socsportstwitter")){
			//pesquisa feita em fun��o da l�ngua e fonte
			AgregaDadosVisualizacao.agregaPorLinguaFonte(lingua, fonte_info, vec_hashtags);
		}
		
		else if(!tipo_mensagem.equals("geralinfo") && !lingua.equals("portuguesingles")&& !fonte_info.equals("socsportstwitter")){
			//pesquisa feita em fun��o do tipo de informacao, l�ngua e fonte
			AgregaDadosVisualizacao.agregaPorTipoLinguaFonte(tipo_mensagem, lingua, fonte_info, vec_hashtags);
		}
	}

	//devolve as hashtags de equipas, jogadores e eventos preferidas do utilizador
	private static Vector<String> devolveTodasHashtagsPref() {
		// TODO Auto-generated method stub
		Vector<String> vec_hashtags = new Vector<String>();
		Vector<String> vec_hashequipas = devolveDescHashEquipas();
		Vector<String> vec_hashjogadores = devolveDescHashJogadores();
		Vector<String> vec_hasheventos = devolveDescHashEventos();
		
		vec_hashtags.addAll(vec_hashequipas);
		vec_hashtags.addAll(vec_hashjogadores);
		vec_hashtags.addAll(vec_hasheventos);
		
		return vec_hashtags;
	}

	private static Vector<String> devolveDescHashEventos() {
		// TODO Auto-generated method stub
		Vector<Integer> vec_idhasheventos = HashEvento.devolveHashIdEventoUserBD();
		Vector<String> vec_deschasheventos = new Vector<String>();
		
		for(int i= 0; i!= vec_idhasheventos.size(); i++){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
				String sql = "select deschash_evento from hashevento where id_hashevento = '" +
				vec_idhasheventos.elementAt(i) + "'";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()){
					vec_deschasheventos.add(rs.getString(1));
				}
				conn.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vec_deschasheventos;
	}

	private static Vector<String> devolveDescHashJogadores() {
		// TODO Auto-generated method stub
		Vector<Integer> vec_idhashjogadores = HashJogador.devolveHashIdJogadorUserBD();
		Vector<String> vec_deschashjogadores = new Vector<String>();
		
		for(int i= 0; i!= vec_idhashjogadores.size(); i++){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
				String sql = "select deschash_jogador from hashjogador where id_hashjogador = '" +
				vec_idhashjogadores.elementAt(i) + "'";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()){
					vec_deschashjogadores.add(rs.getString(1));
				}
				conn.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vec_deschashjogadores;
	}

	private static Vector<String> devolveDescHashEquipas() {
		// TODO Auto-generated method stub
		Vector<Integer> vec_idhashequipas = HashEquipa.devolveHashIdEquipaUserBD();
		Vector<String> vec_deschashequipas = new Vector<String>();
		
		for(int i= 0; i!= vec_idhashequipas.size(); i++){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
				String sql = "select deschash_equipa from hashequipa where id_hashequipa = '" +
				vec_idhashequipas.elementAt(i) + "'";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()){
					vec_deschashequipas.add(rs.getString(1));
				}
				conn.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vec_deschashequipas;
	}
}
