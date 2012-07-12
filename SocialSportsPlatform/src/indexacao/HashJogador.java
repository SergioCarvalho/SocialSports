package indexacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class HashJogador {

	public static Vector<String> devolveHashJogadoresBD() {
		// TODO Auto-generated method stub
		Vector<String> vector_hashjogadores = new Vector<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select deschash_jogador from hashjogador";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vector_hashjogadores.add(rs.getString(1));
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vector_hashjogadores;
	}

	public static void insereHashJogadorBD(String hashtag_jogador, String nome_jogador, String modalidade_jogador) {
		// TODO Auto-generated method stub
		int id_jogador = devolveHashJogadorIdJogador(nome_jogador);
		int id_modalidade = devolveHashJogadorIdModalidade(modalidade_jogador);

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			String sql_insert = "insert into hashjogador (id_hashjogador, id_jogador, deschash_jogador, id_modalidade)" +
			"values (null,'" + id_jogador + "','" + hashtag_jogador + "','" + id_modalidade + "')";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_insert);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private static int devolveHashJogadorIdJogador(String nome_jogador) {
		// TODO Auto-generated method stub
		int id_jogador = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_jogador from jogador where nome_jogador = '" + nome_jogador + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				id_jogador = rs.getInt(1);
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id_jogador;
	}


	private static int devolveHashJogadorIdModalidade(String modalidade_jogador) {
		// TODO Auto-generated method stub
		if(modalidade_jogador.equals("Futebol")){
			return 1;
		}
		else if(modalidade_jogador.equals("Ténis")){
			return 2;
		}

		else if(modalidade_jogador.equals("Basquetebol")){
			return 3;
		}
		else 
			return 1;
	}

	//devolve os id das hashtags de jogadores que o utilizador tem interesse
	public static Vector<Integer> devolveHashIdsJogadores(Vector<String> vec_hashjogadores) {
		// TODO Auto-generated method stub
		Vector<Integer> vec_userhashidsjogadores = new Vector<Integer>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			for(int i=0;i!= vec_hashjogadores.size(); i++){
				String sql = "select id_hashjogador from hashjogador " +
				"where deschash_jogador = '" + vec_hashjogadores.elementAt(i) + "'";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()){
					vec_userhashidsjogadores.add(rs.getInt(1));
				}
				//				conn.close();
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec_userhashidsjogadores;
	}

	public static Vector<Integer> devolveHashIdJogadorUserBD() {
		// TODO Auto-generated method stub
		int id_utilizador = 1;
		Vector<Integer> vector_hashidjogadoresuser = new Vector<Integer>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashjogador from utilizadorhashjogador where id_utilizador = '" + id_utilizador + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vector_hashidjogadoresuser.add(rs.getInt(1));
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vector_hashidjogadoresuser;
	}

	public static void insereHashJogadorUserBD(Integer id_hashjogador) {
		// TODO Auto-generated method stub
		int id_jogador = devolveIdJogadorPorIdHashJogador(id_hashjogador);
		int id_modalidade = devolveIdModalidadePorIdHashJogador(id_hashjogador);

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			String sql_insert = "insert into utilizadorhashjogador (id_utilizador, id_hashjogador, id_jogador, id_modalidade)" +
			"values (1,'" + id_hashjogador + "','" + id_jogador + "','" + id_modalidade + "')";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_insert);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static int devolveIdJogadorPorIdHashJogador(Integer id_hashjogador) {
		// TODO Auto-generated method stub
		int id_jogador = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_jogador from hashjogador where id_hashjogador = '" + id_hashjogador + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				id_jogador = rs.getInt(1);
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id_jogador;
	}

	private static int devolveIdModalidadePorIdHashJogador(Integer id_hashjogador) {
		// TODO Auto-generated method stub
		int id_modalidade = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_modalidade from hashjogador where id_hashjogador = '" + id_hashjogador + "'";
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

	public static Vector<Integer> devolveHashIdJogadorUserBDMod(Vector<String> vec_modalidades) {
		// TODO Auto-generated method stub
		Vector<Integer> vec_idhashjogadores = new Vector<Integer>();
		int id_modalidade = devolveHashJogadorIdModalidade(vec_modalidades.elementAt(0));
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashjogador from utilizadorhashjogador where id_modalidade = '" + id_modalidade + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vec_idhashjogadores.add(rs.getInt(1));
				System.out.println("ID encontrado: " + rs.getInt(1));
			}
			conn.close();

			System.out.println("Fim modalidade");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vec_idhashjogadores;
	}

	public static void removeHashJogadorUserBD(Integer id_hashjogador) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			String sql_delete = "delete from utilizadorhashjogador where id_hashjogador = '" + id_hashjogador + "'";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_delete);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}

	//elimina da tabela do utilizador de hashtags relativas a jogadores baseado na modalidade (já não existem jogadores
	//relativos a essa modalidade)
	public static void eliminaHashJogadorMod(String nome_modalidade) {
		// TODO Auto-generated method stub
		int id_modalidade = devolveHashJogadorIdModalidade(nome_modalidade);

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			String sql_delete = "delete from utilizadorhashjogador where id_modalidade = '" + id_modalidade + "'";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_delete);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//método que actualiza os dados das hashtags de jogador caso o utilizador os tenha mudado na app móvel
	public static void actualizaHashjogador(String hash_jogador, String jogador, String modalidade) {
		// TODO Auto-generated method stub
		//vector com id_jogador e id_modalidade para comparar e verificar se é necessário actualizar
		Vector<Integer> vec_hashjogbd = new Vector<Integer>();
		vec_hashjogbd = devolveIdsHashJogadorBD(hash_jogador);

		int id_hashjogadorbd = vec_hashjogbd.elementAt(0);
		int id_jogadorbd = vec_hashjogbd.elementAt(1);
		int id_modalidadebd = vec_hashjogbd.elementAt(2);
		int id_jogadorappm = Jogador.devolveJogadorIdJogador(jogador);
		int id_modalidadeappm = Jogador.devolveJogadorIdModalidade(modalidade);

		//se o id do jogador ou o id da modalidade associado a uma hashtag estiverem incorrectos na bd
		//na tabela hashjogador ele actualiza os valores
		if(id_jogadorappm != id_jogadorbd || id_modalidadeappm != id_modalidadebd){
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
				Statement stmt = conn.createStatement();
				String sql = "UPDATE hashjogador SET id_jogador = '" + id_jogadorappm + "', " + 
				"id_modalidade = '" + id_modalidadeappm + "' " + "WHERE id_hashjogador = '" + id_hashjogadorbd + "'";
				stmt.executeUpdate(sql);				
				conn.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Erro na actualizacao da hashtag de jogador na tabela hashjogador");
			}
		} 

	}

	//devolve id_hashjogador, id_jogador e o id_modalidade de uma hashtag de jogador baseado na descrição da hashtag de jogador
	private static Vector<Integer> devolveIdsHashJogadorBD(String hash_jogador) {
		// TODO Auto-generated method stub
		Vector<Integer> vec_hashjogbd = new Vector<Integer>();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashjogador, id_jogador, id_modalidade from hashjogador where deschash_jogador = '" + hash_jogador + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vec_hashjogbd.add(rs.getInt(1));
				vec_hashjogbd.add(rs.getInt(2));
				vec_hashjogbd.add(rs.getInt(3));
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec_hashjogbd;
	}

	public static void actualizaUtilizadorHashJogador(String hash_jogador, String jogador, String modalidade) {
		// TODO Auto-generated method stub
		//vector com todos os id_hashjogador existentes na tabela utilizadorhashjogador
		Vector<Integer> vec_userhashjogadorbd = new Vector<Integer>();
		vec_userhashjogadorbd = devolveIdsHashjogadorUtilizadorHashJogador();

		//id_hashjogador da hashtag de jogador existente na appmovel a ser verificada 
		int id_hashjogadorbd = devolveIdHashJogadorPorDescHashJogador(hash_jogador);

		//vector com o id_jogador e id_modalidade correspondentes ao id_hashjogador da hashtag de jogador existente 
		//na app a ser verificado 
		Vector<Integer> vec_hashjogbd = new Vector<Integer>();
		vec_hashjogbd = devolveIdsHashJogadorBD(id_hashjogadorbd);

		int id_jogadorappm = Jogador.devolveJogadorIdJogador(jogador);
		int id_modalidadeappm = Jogador.devolveJogadorIdModalidade(modalidade);

		//caso tenhamos entradas na tabela utilizadorhashjogador
		if(vec_hashjogbd.size()>0){
			int id_jogadorbd = vec_hashjogbd.elementAt(0);
			int id_modalidadebd = vec_hashjogbd.elementAt(1);


			//se o id_hashjogador ja existir na tabela utilizadorhashjogador verifica se deve fazer a actualizacao
			if(vec_userhashjogadorbd.contains(id_hashjogadorbd)){

				//se o id do jogador ou o id da modalidade associado a uma hashtag estiverem incorrectos na bd
				//na tabela utilizadorhashjogador ele actualiza os valores
				if(id_jogadorappm != id_jogadorbd || id_modalidadeappm != id_modalidadebd){
					try {
						Class.forName("com.mysql.jdbc.Driver").newInstance();
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
						Statement stmt = conn.createStatement();
						String sql = "UPDATE utilizadorhashjogador SET id_jogador = '" + id_jogadorappm + "', " + 
						"id_modalidade = '" + id_modalidadeappm + "' " + "WHERE id_hashjogador = '" + id_hashjogadorbd + "'";
						stmt.executeUpdate(sql);				
						conn.close();

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("Erro na actualizacao dos dados utilizadorhashtagequipa");
					}
				}	
			}
		}
		//caso contrário insere a nova entrada na tabela utilizadorhashjogador tendo por base o id_hashjogador
		else{
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
				Statement stmt = conn.createStatement();
				String sql_insert = "insert into utilizadorhashjogador (id_utilizador, id_hashjogador, id_jogador, id_modalidade)" +
				"values (1,'" + id_hashjogadorbd + "','" + id_jogadorappm + "','" + id_modalidadeappm + "')";
				stmt.executeUpdate(sql_insert);				
				conn.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Erro na actualizacao dos dados utilizadorhashjogador");
			}
		}
	}

	private static Vector<Integer> devolveIdsHashJogadorBD(int id_hashjogadorbd) {
		// TODO Auto-generated method stub
		Vector<Integer> vec_hashjoguserbd = new Vector<Integer>();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_jogador, id_modalidade from utilizadorhashjogador where id_hashjogador = '" + id_hashjogadorbd + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vec_hashjoguserbd.add(rs.getInt(1));
				vec_hashjoguserbd.add(rs.getInt(2));
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec_hashjoguserbd;
	}

	//devolve o id_hashjogador da tabela hashjogador tendo por base o nome da hashtag
	private static int devolveIdHashJogadorPorDescHashJogador(String hash_jogador) {
		// TODO Auto-generated method stub
		int id_hashjogador = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashjogador from hashjogador where deschash_jogador = '" + hash_jogador + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				id_hashjogador = rs.getInt(1);
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id_hashjogador;
	}

	//método que devolve todos os id_hashjogador da tabela utilizadorhashjogador
	private static Vector<Integer> devolveIdsHashjogadorUtilizadorHashJogador() {
		// TODO Auto-generated method stub
		Vector<Integer> vec_useridhashjogador = new Vector<Integer>();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashjogador from utilizadorhashjogador";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vec_useridhashjogador.add(rs.getInt(1));
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec_useridhashjogador;
	}

	//método que elimina entradas da tabela utilizadorhashjogador para hashtags de jogadores que já não existem na app móvel
	public static void eliminaDadosUtilizadorHashJogador(Vector<String> vec_hashjogadorappm) {
		// TODO Auto-generated method stub
		//vector com todos os id_hashjogador dos jogadores existentes na app_móvel
		Vector<Integer> vec_idhashjogadoresappm = new Vector<Integer>();
		vec_idhashjogadoresappm = devolveHashIdsJogadores(vec_hashjogadorappm);

		//vector com todos os id_hashjogador existentes na tabela utilizadorhashjogador
		Vector<Integer> vecuser_idhashjogadorbd = new Vector<Integer>();
		vecuser_idhashjogadorbd = devolveIdsHashjogadorUtilizadorHashJogador();

		//vai verificar para todos os id_hashjogador existente na tabela utilizadorhashjogador se estes existem na app móvel
		for(int i = 0; i!= vecuser_idhashjogadorbd.size(); i++){
			//se o id não existir na app movel ele apaga da tabela utilizadorhashjogador
			if(!vec_idhashjogadoresappm.contains(vecuser_idhashjogadorbd.elementAt(i))){
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

					String sql_delete = "delete from utilizadorhashjogador where id_hashjogador = '" + vecuser_idhashjogadorbd.elementAt(i) + "'";
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

	public static int devolveIdModalidePorDescHashJogador(String deschash_jogador) {
		// TODO Auto-generated method stub
		int id_modalidade = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_modalidade from hashjogador where deschash_jogador = '" + deschash_jogador + "'";
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
