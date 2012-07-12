package indexacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class HashEvento {

	public static Vector<String> devolveHashEventosBD() {
		// TODO Auto-generated method stub
		Vector<String> vector_hasheventos = new Vector<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select deschash_evento from hashevento";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vector_hasheventos.add(rs.getString(1));
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vector_hasheventos;
	}

	public static void insereHashEventoBD(String hashtag_evento, String nome_evento, String modalidade_evento) {
		// TODO Auto-generated method stub
		int id_evento = devolveHashEventoIdEvento(nome_evento);
		int id_modalidade = devolveHashEventoIdModalidade(modalidade_evento);

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			String sql_insert = "insert into hashevento (id_hashevento, id_evento, deschash_evento, id_modalidade)" +
			"values (null,'" + id_evento + "','" + hashtag_evento + "','" + id_modalidade + "')";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_insert);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static int devolveHashEventoIdEvento(String nome_evento) {
		// TODO Auto-generated method stub
		int id_evento = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_evento from evento where nome_evento = '" + nome_evento + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				id_evento = rs.getInt(1);
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id_evento;
	}

	private static int devolveHashEventoIdModalidade(String modalidade_evento) {
		// TODO Auto-generated method stub
		if(modalidade_evento.equals("Futebol")){
			return 1;
		}
		else if(modalidade_evento.equals("Ténis")){
			return 2;
		}

		else if(modalidade_evento.equals("Basquetebol")){
			return 3;
		}
		else 
			return 1;
	}

	public static Vector<Integer> devolveHashIdsEventos(Vector<String> vec_hasheventos) {
		// TODO Auto-generated method stub
		Vector<Integer> vec_userhashidseventos = new Vector<Integer>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			for(int i=0;i!= vec_hasheventos.size(); i++){
				String sql = "select id_hashevento from hashevento " +
				"where deschash_evento = '" + vec_hasheventos.elementAt(i) + "'";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()){
					vec_userhashidseventos.add(rs.getInt(1));
				}
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec_userhashidseventos;
	}

	public static Vector<Integer> devolveHashIdEventoUserBD() {
		// TODO Auto-generated method stub
		int id_utilizador = 1;
		Vector<Integer> vector_hashideventosuser = new Vector<Integer>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashevento from utilizadorhashevento where id_utilizador = '" +
			id_utilizador + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vector_hashideventosuser.add(rs.getInt(1));
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vector_hashideventosuser;
	}

	public static void insereHashEventoUserBD(Integer id_hashevento) {
		// TODO Auto-generated method stub
		int id_evento = devolveIdEventoPorIdHashEvento(id_hashevento);
		int id_modalidade = devolveIdModalidadePorIdHashEvento(id_hashevento);

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			String sql_insert = "insert into utilizadorhashevento (id_utilizador, id_hashevento, id_evento, id_modalidade)" +
			"values (1,'" + id_hashevento + "','" + id_evento + "','" + id_modalidade + "')";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_insert);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

	private static int devolveIdModalidadePorIdHashEvento(Integer id_hashevento) {
		// TODO Auto-generated method stub
		int id_modalidade = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_modalidade from hashevento where id_hashevento = '" + id_hashevento + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				id_modalidade = rs.getInt(1);
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id_modalidade;
	}

	private static int devolveIdEventoPorIdHashEvento(Integer id_hashevento) {
		// TODO Auto-generated method stub
		int id_evento = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_evento from hashevento where id_hashevento = '" + id_hashevento + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				id_evento = rs.getInt(1);
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id_evento;
	}

	public static Vector<Integer> devolveHashIdEventoUserBDMod(Vector<String> vec_modalidades) {
		// TODO Auto-generated method stub
		Vector<Integer> vec_idhasheventos = new Vector<Integer>();
		int id_modalidade = devolveHashEventoIdModalidade(vec_modalidades.elementAt(0));
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashevento from utilizadorhashevento where id_modalidade = '" + id_modalidade + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vec_idhasheventos.add(rs.getInt(1));
				//				System.out.println("ID encontrado: " + rs.getInt(1));
			}
			conn.close();

			//			System.out.println("Fim modalidade");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vec_idhasheventos;
	}

	public static void removeHashEventoUserBD(Integer id_hashevento) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			String sql_delete = "delete from utilizadorhashevento where id_hashevento = '" + id_hashevento + "'";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_delete);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

	//elimina da tabela do utilizador de hashtags relativas a eventos baseado na modalidade (já não existem eventos
	//relativos a essa modalidade)
	public static void eliminaHashEventoMod(String nome_modalidade) {
		// TODO Auto-generated method stub
		int id_modalidade = devolveHashEventoIdModalidade(nome_modalidade);

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			String sql_delete = "delete from utilizadorhashevento where id_modalidade = '" + id_modalidade + "'";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_delete);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//método que actualiza os dados das hashtags de eventos caso o utilizador os tenha mudado na app móvel
	public static void actualizaHashevento(String hash_evento, String evento, String modalidade) {
		// TODO Auto-generated method stub
		//vector com id_evento e id_modalidade para comparar e verificar se é necessário actualizar
		Vector<Integer> vec_hashevbd = new Vector<Integer>();
		vec_hashevbd = devolveIdsHashEventoBD(hash_evento);

		int id_hasheventobd = vec_hashevbd.elementAt(0);
		int id_eventobd = vec_hashevbd.elementAt(1);
		int id_modalidadebd = vec_hashevbd.elementAt(2);
		int id_eventoappm = Evento.devolveEventoIdEvento(evento);
		int id_modalidadeappm = Evento.devolveEventoIdModalidade(modalidade);

		//se o id do evento ou o id da modalidade associado a uma hashtag estiverem incorrectos na bd
		//na tabela hashevento ele actualiza os valores
		if(id_eventoappm != id_eventobd || id_modalidadeappm != id_modalidadebd){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
				Statement stmt = conn.createStatement();
				String sql = "UPDATE hashevento SET id_evento = '" + id_eventoappm + "', " + 
				"id_modalidade = '" + id_modalidadeappm + "' " + "WHERE id_hashevento = '" + id_hasheventobd + "'";
				stmt.executeUpdate(sql);				
				conn.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Erro na actualizacao da hashtag de evento");
			}
		} 
	}

	//devolve id_hashevento, id_evento e o id_modalidade de uma hashtag de evento baseado na descrição da hashtag de evento
	private static Vector<Integer> devolveIdsHashEventoBD(String hash_evento) {
		// TODO Auto-generated method stub
		Vector<Integer> vec_hashevbd = new Vector<Integer>();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashevento, id_evento, id_modalidade from hashevento where deschash_evento = '" + hash_evento + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vec_hashevbd.add(rs.getInt(1));
				vec_hashevbd.add(rs.getInt(2));
				vec_hashevbd.add(rs.getInt(3));
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec_hashevbd;
	}

	public static void actualizaUtilizadorHashEvento(String hash_evento, String evento, String modalidade) {
		// TODO Auto-generated method stub
		//vector com todos os id_hashevento existentes na tabela utilizadorhashevento
		Vector<Integer> vec_userhasheventobd = new Vector<Integer>();
		vec_userhasheventobd = devolveIdsHasheventoUtilizadorHashEvento();

		//id_hashevento da hashtag de evento existente na appmovel a ser verificada 
		int id_hasheventobd = devolveIdHashEventoPorDescHashEvento(hash_evento);

		//vector com o id_evento e id_modalidade correspondentes ao id_hashevento da hashtag de evento existente 
		//na app a ser verificado 
		Vector<Integer> vec_hashevbd = new Vector<Integer>();
		vec_hashevbd = devolveIdsHashEventoBD(id_hasheventobd);

		int id_eventoappm = Evento.devolveEventoIdEvento(evento);
		int id_modalidadeappm = Evento.devolveEventoIdModalidade(modalidade);

		//caso tenhamos entradas na tabela utilizadorhashevento
		if(vec_hashevbd.size()>0){
			int id_eventobd = vec_hashevbd.elementAt(0);
			int id_modalidadebd = vec_hashevbd.elementAt(1);


			//se o id_hashevento ja existir na tabela utilizadorhashevento verifica se deve fazer a actualizacao
			if(vec_userhasheventobd.contains(id_hasheventobd)){

				//se o id do evento ou o id da modalidade associado a uma hashtag estiverem incorrectos na bd
				//na tabela utilizadorhashevento ele actualiza os valores
				if(id_eventoappm != id_eventobd || id_modalidadeappm != id_modalidadebd){
					try {
						Class.forName("com.mysql.jdbc.Driver").newInstance();
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
						Statement stmt = conn.createStatement();
						String sql = "UPDATE utilizadorhashevento SET id_evento = '" + id_eventoappm + "', " + 
						"id_modalidade = '" + id_modalidadeappm + "' " + "WHERE id_hashevento = '" + id_hasheventobd + "'";
						stmt.executeUpdate(sql);				
						conn.close();

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("Erro na actualizacao dos dados utilizadorhashtagevento");
					}
				}	
			}
		}
		//caso contrário insere a nova entrada na tabela utilizadorhashevento tendo por base o id_hashevento
		else{
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
				Statement stmt = conn.createStatement();
				String sql_insert = "insert into utilizadorhashevento (id_utilizador, id_hashevento, id_evento, id_modalidade)" +
				"values (1,'" + id_hasheventobd + "','" + id_eventoappm + "','" + id_modalidadeappm + "')";
				stmt.executeUpdate(sql_insert);				
				conn.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Erro na actualizacao dos dados utilizadorhashequipa");
			}
		}
	}

	//método que devolve os id_evento e id_modalidade da tabela utilizadorhashevento tendo por base o id_hashevento
	private static Vector<Integer> devolveIdsHashEventoBD(int id_hasheventobd) {
		// TODO Auto-generated method stub
		Vector<Integer> vec_hashevuserbd = new Vector<Integer>();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_evento, id_modalidade from utilizadorhashevento where id_hashevento = '" + id_hasheventobd + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vec_hashevuserbd.add(rs.getInt(1));
				vec_hashevuserbd.add(rs.getInt(2));
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec_hashevuserbd;
	}

	//devolve o id_hashevento da tabela hashevento tendo por base o nome da hashtag
	private static int devolveIdHashEventoPorDescHashEvento(String hash_evento) {
		// TODO Auto-generated method stub
		int id_hashevento = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashevento from hashevento where deschash_evento = '" + hash_evento + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				id_hashevento = rs.getInt(1);
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id_hashevento;
	}

	//método que devolve todos os id_hashevento da tabela utilizadorhashevento
	private static Vector<Integer> devolveIdsHasheventoUtilizadorHashEvento() {
		// TODO Auto-generated method stub
		Vector<Integer> vec_useridhashevento = new Vector<Integer>();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashevento from utilizadorhashevento";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vec_useridhashevento.add(rs.getInt(1));
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec_useridhashevento;
	}

	//método que elimina entradas da tabela utilizadorhashevento para hashtags de eventos que já não existem na app móvel
	public static void eliminaDadosUtilizadorHashEvento( Vector<String> vec_idhasheventoappm) {
		// TODO Auto-generated method stub
		//vector com todos os id_hashevento dos eventos existentes na app_móvel
		Vector<Integer> vec_idhasheventosappm = new Vector<Integer>();
		vec_idhasheventosappm = devolveHashIdsEventos(vec_idhasheventoappm);

		//vector com todos os id_hashevento existentes na tabela utilizadorhashevento
		Vector<Integer> vecuser_idhasheventobd = new Vector<Integer>();
		vecuser_idhasheventobd = devolveIdsHasheventoUtilizadorHashEvento();

		//vai verificar para todos os id_hashevento existente na tabela utilizadorhashevento se estes existem na app móvel
		for(int i = 0; i!= vecuser_idhasheventobd.size(); i++){
			//se o id não existir na app movel ele apaga da tabela utilizadorhashevento
			if(!vec_idhasheventosappm.contains(vecuser_idhasheventobd.elementAt(i))){
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

					String sql_delete = "delete from utilizadorhashevento where id_hashevento = '" + vecuser_idhasheventobd.elementAt(i) + "'";
					Statement stmt_insert = conn.createStatement();
					stmt_insert.executeUpdate(sql_delete);
					conn.close();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	//devolve o id_modalidade da tabela hashevento tendo por base o nome da hashtag
	public static int devolveIdModalidePorDescHashEvento(String deschash_evento) {
		// TODO Auto-generated method stub
		int id_modalidade = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_modalidade from hashevento where deschash_evento = '" + deschash_evento + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				id_modalidade = rs.getInt(1);
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id_modalidade;
	}
}
