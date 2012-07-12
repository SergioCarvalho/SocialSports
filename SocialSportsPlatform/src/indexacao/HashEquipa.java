package indexacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class HashEquipa {

	//método que devolve todos os nomes das hashtags de equipas existentes na BD
	public static Vector<String> devolveHashEquipasBD() {
		// TODO Auto-generated method stub
		Vector<String> vector_hashequipas = new Vector<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select deschash_equipa from hashequipa";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vector_hashequipas.add(rs.getString(1));
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vector_hashequipas;
	}

	public static void insereHashEquipaBD(String hashtag_equipa, String nome_equipa, String modalidade_equipa) {
		// TODO Auto-generated method stub
		int id_equipa = devolveHashEquipaIdEquipa(nome_equipa);
		int id_modalidade = devolveHashEquipaIdModalidade(modalidade_equipa);

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			String sql_insert = "insert into hashequipa (id_hashequipa, id_equipa, deschash_equipa, id_modalidade)" +
			"values (null,'" + id_equipa + "','" + hashtag_equipa + "','" + id_modalidade + "')";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_insert);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//metodo que devolve o id da equipa tendo por base o nome da equipa
	private static int devolveHashEquipaIdEquipa(String nome_equipa) {
		// TODO Auto-generated method stub
		int id_equipa = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_equipa from equipa where nome_equipa = '" + nome_equipa + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				id_equipa = rs.getInt(1);
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id_equipa;
	}

	private static int devolveHashEquipaIdModalidade(String modalidade_equipa) {
		if(modalidade_equipa.equals("Futebol")){
			return 1;
		}
		else if(modalidade_equipa.equals("Ténis")){
			return 2;
		}

		else if(modalidade_equipa.equals("Basquetebol")){
			return 3;
		}
		else 
			return 1;
	}

	//devolve os id das hashtags de equipas do interesse do utilizador existentes na BD 
	public static Vector<Integer> devolveHashIdEquipaUserBD() {
		// TODO Auto-generated method stub
		int id_utilizador = 1;
		Vector<Integer> vector_hashidequipasuser = new Vector<Integer>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashequipa from utilizadorhashequipa where id_utilizador = '" +
			id_utilizador + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vector_hashidequipasuser.add(rs.getInt(1));
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vector_hashidequipasuser;
	}

	//devolve os id das hashtags de equipas que o utilizador tem interesse
	public static Vector<Integer> devolveHashIdsEquipas(Vector<String> vec_hashequipas) {
		// TODO Auto-generated method stub
		Vector<Integer> vec_userhashidsequipas = new Vector<Integer>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			for(int i=0;i!= vec_hashequipas.size(); i++){
				String sql = "select id_hashequipa from hashequipa " +	"where deschash_equipa = '" + vec_hashequipas.elementAt(i) + "'";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()){
					vec_userhashidsequipas.add(rs.getInt(1));
				}
				//				conn.close();
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec_userhashidsequipas;
	}

	//método que insere os ids das hashtags de interesse do utilizador na tabela utilizadorhashequipa
	//é a partir da tabela utilizadorhashequipa que vamos ler os interesses do utilizador para os disponibilizarmos na app movel
	public static void insereHashEquipaUserBD(Integer id_hashequipa) {
		// TODO Auto-generated method stub
		int id_equipa = devolveIdEquipaPorIdHashEquipa(id_hashequipa);
		int id_modalidade = devolveIdModalidadePorIdHashEquipa(id_hashequipa);

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			String sql_insert = "insert into utilizadorhashequipa (id_utilizador, id_hashequipa, id_equipa, id_modalidade)" +
			"values (1,'" + id_hashequipa + "','" + id_equipa + "','" + id_modalidade + "')";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_insert);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	//método que devolve o id modalidade da equipa tendo por base o id_hashequipa da tabela hashequipa
	private static int devolveIdModalidadePorIdHashEquipa(Integer id_hashequipa) {
		// TODO Auto-generated method stub
		int id_modalidade = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_modalidade from hashequipa where id_hashequipa = '" + id_hashequipa + "'";
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

	//método que devolve o id da equipa tendo por base o id_hashequipa da tabela hashequipa
	private static int devolveIdEquipaPorIdHashEquipa(Integer id_hashequipa) {
		// TODO Auto-generated method stub
		int id_equipa = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_equipa from hashequipa where id_hashequipa = '" + id_hashequipa + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				id_equipa = rs.getInt(1);
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id_equipa;
	}

	//método que devolve o id modadalide tendo por base a deschash_equipa da tabela hashequipa
	public static int devolveIdModalidePorDescHashEquipa(String deschash_equipa) {
		// TODO Auto-generated method stub
		int id_modalidade = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_modalidade from hashequipa where deschash_equipa = '" + deschash_equipa + "'";
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
	//método que remove as entradas na tabela utilizadorhashequipa que o utilizador já não tem interesse
	public static void removeHashEquipaUserBD(Integer id_hashequipa) {
		// TODO Auto-generated method stub
		System.out.println("Entrou no remove hashequipas do utilizador para id: " + id_hashequipa); 
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			String sql_delete = "delete from utilizadorhashequipa where id_hashequipa = '" + id_hashequipa + "'";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_delete);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	//devolve os id das hashtags de equipas do interesse do utilizador existentes na BD por modalidade
	public static Vector<Integer> devolveHashIdEquipaUserBDMod(Vector<String> vec_modalidades) {
		// TODO Auto-generated method stub
		Vector<Integer> vec_idhashequipas = new Vector<Integer>();
		int id_modalidade = devolveHashEquipaIdModalidade(vec_modalidades.elementAt(0));
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashequipa from utilizadorhashequipa where id_modalidade = '" + id_modalidade + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vec_idhashequipas.add(rs.getInt(1));
				//				System.out.println("ID encontrado: " + rs.getInt(1));
			}
			conn.close();

			//			System.out.println("Fim modalidade");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vec_idhashequipas;
	}

	//elimina da tabela do utilizador de hashtags relativas a equipas baseado na modalidade (já não existem equipas
	//relativos a essa modalidade)
	public static void eliminaHashEquipaMod(String nome_modalidade) {
		// TODO Auto-generated method stub
		int id_modalidade = devolveHashEquipaIdModalidade(nome_modalidade);

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			String sql_delete = "delete from utilizadorhashequipa where id_modalidade = '" + id_modalidade + "'";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_delete);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//método que actualiza os dados das hashtags de equipas caso o utilizador os tenha mudado na app móvel
	public static void actualizaHashequipa(String hash_equipa, String equipa, String modalidade) {
		// TODO Auto-generated method stub
		//vector com id_equipa e id_modalidade para comparar e verificar se é necessário actualizar
		Vector<Integer> vec_hasheqbd = new Vector<Integer>();
		vec_hasheqbd = devolveIdsHashEquipaBD(hash_equipa);

		int id_hashequipabd = vec_hasheqbd.elementAt(0);
		int id_equipabd = vec_hasheqbd.elementAt(1);
		int id_modalidadebd = vec_hasheqbd.elementAt(2);
		int id_equipaappm = Equipa.devolveEquipaIdEquipa(equipa);
		int id_modalidadeappm = Equipa.devolveEquipaIdModalidade(modalidade);

		//se o id da equipa ou o id da modalidade associado a uma hashtag estiverem incorrectos na bd
		//na tabela hashequipa ele actualiza os valores
		if(id_equipaappm != id_equipabd || id_modalidadeappm != id_modalidadebd){
			//System.out.println("Entrou aqui pela hashtag com id: " + id_hashequipabd);
			//System.out.println("Vai mudar o: " + hash_equipa);
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
				Statement stmt = conn.createStatement();
				String sql = "UPDATE hashequipa SET id_equipa = '" + id_equipaappm + "', " + 
				"id_modalidade = '" + id_modalidadeappm + "' " + "WHERE id_hashequipa = '" + id_hashequipabd + "'";
				stmt.executeUpdate(sql);				
				conn.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Erro na actualizacao da hashtag de equipa");
			}
		} 
	}

	//devolve id_hashequipa, id_equipa e o id_modalidade de uma hashtag de equipa baseado na descrição da hashtag de equipa
	private static Vector<Integer> devolveIdsHashEquipaBD(String hash_equipa) {
		// TODO Auto-generated method stub
		Vector<Integer> vec_hasheqbd = new Vector<Integer>();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashequipa, id_equipa, id_modalidade from hashequipa where deschash_equipa = '" + hash_equipa + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vec_hasheqbd.add(rs.getInt(1));
				vec_hasheqbd.add(rs.getInt(2));
				vec_hasheqbd.add(rs.getInt(3));
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec_hasheqbd;
	}

	public static void actualizaUtilizadorHashEquipa(String hash_equipa, String equipa, String modalidade) {
		// TODO Auto-generated method stub
		//vector com todos os id_hashequipa existentes na tabela utilizadorhashequipa
		Vector<Integer> vec_userhashequipabd = new Vector<Integer>();
		vec_userhashequipabd = devolveIdsHashequipaUtilizadorHashEquipa();

		//id_hashequipa da hashtag de equipa existente na appmovel a ser verificada 
		int id_hashequipabd = devolveIdHashEquipaPorDescHashEquipa(hash_equipa);

		//vector com o id_equipa e id_modalidade correspondentes ao id_hashequipa da hashtag de equipa existente 
		//na app a ser verificado 
		Vector<Integer> vec_hasheqbd = new Vector<Integer>();
		vec_hasheqbd = devolveIdsHashEquipaBD(id_hashequipabd);

		int id_equipaappm = Equipa.devolveEquipaIdEquipa(equipa);
		int id_modalidadeappm = Equipa.devolveEquipaIdModalidade(modalidade);
		
		//caso tenhamos entradas na tabela utilizadorhashequipa
		if(vec_hasheqbd.size()>0){
			int id_equipabd = vec_hasheqbd.elementAt(0);
			int id_modalidadebd = vec_hasheqbd.elementAt(1);
			

			//se o id_hashequipa ja existir na tabela utilizadorhashequipa verifica se deve fazer a actualizacao
			if(vec_userhashequipabd.contains(id_hashequipabd)){

				//se o id da equipa ou o id da modalidade associado a uma hashtag estiverem incorrectos na bd
				//na tabela utilizadorhashequipa ele actualiza os valores
				if(id_equipaappm != id_equipabd || id_modalidadeappm != id_modalidadebd){
					try {
						Class.forName("com.mysql.jdbc.Driver").newInstance();
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
						Statement stmt = conn.createStatement();
						String sql = "UPDATE utilizadorhashequipa SET id_equipa = '" + id_equipaappm + "', " + 
						"id_modalidade = '" + id_modalidadeappm + "' " + "WHERE id_hashequipa = '" + id_hashequipabd + "'";
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
		//caso contrário insere a nova entrada na tabela utilizadorhashequipa tendo por base o id_hashequipa
		else{
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
				Statement stmt = conn.createStatement();
				String sql_insert = "insert into utilizadorhashequipa (id_utilizador, id_hashequipa, id_equipa, id_modalidade)" +
				"values (1,'" + id_hashequipabd + "','" + id_equipaappm + "','" + id_modalidadeappm + "')";
				stmt.executeUpdate(sql_insert);				
				conn.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Erro na actualizacao dos dados utilizadorhashequipa");
			}
		}
	}

	//método que devolve todos os id_hashequipa da tabela utilizadorhashequipa
	private static Vector<Integer> devolveIdsHashequipaUtilizadorHashEquipa() {
		// TODO Auto-generated method stub
		Vector<Integer> vec_useridhashequipa = new Vector<Integer>();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashequipa from utilizadorhashequipa";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vec_useridhashequipa.add(rs.getInt(1));
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec_useridhashequipa;
	}

	//devolve o id_hashequipa da tabela hashequipa tendo por base o nome da hashtag
	private static int devolveIdHashEquipaPorDescHashEquipa(String hash_equipa) {
		// TODO Auto-generated method stub
		int id_hashequipa = 0;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_hashequipa from hashequipa where deschash_equipa = '" + hash_equipa + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				id_hashequipa = rs.getInt(1);
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id_hashequipa;
	}

	//método que devolve os id_equipa e modalidade da tabela utilizadorhashequipa tendo por base o id_hashequipa
	private static Vector<Integer> devolveIdsHashEquipaBD(int id_hashequipa) {
		// TODO Auto-generated method stub
		Vector<Integer> vec_hashequserbd = new Vector<Integer>();

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_equipa, id_modalidade from utilizadorhashequipa where id_hashequipa = '" + id_hashequipa + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vec_hashequserbd.add(rs.getInt(1));
				vec_hashequserbd.add(rs.getInt(2));
			}
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec_hashequserbd;
	}

	//método que elimina entradas da tabela utilizadorhashequipa para hashtags de equipas que já não existem na app móvel
	public static void eliminaDadosUtilizadorHashEquipa(Vector<String> vec_hashequipasappm) {
		// TODO Auto-generated method stub
		//vector com todos os id_hashequipa das equipas existentes na app_móvel
		Vector<Integer> vec_idhashequipasappm = new Vector<Integer>();
		vec_idhashequipasappm = devolveHashIdsEquipas(vec_hashequipasappm);

		//vector com todos os id_hashequipa existentes na tabela utilizadorhashequipa
		Vector<Integer> vecuser_idhashequipabd = new Vector<Integer>();
		vecuser_idhashequipabd = devolveIdsHashequipaUtilizadorHashEquipa();

		//vai verificar para todos os id_hashequipa existente na tabela utilizadorhashequipa se estes existem na app móvel
		for(int i = 0; i!= vecuser_idhashequipabd.size(); i++){
			//se o id não existir na app movel ele apaga da tabela utilizadorhashequipa
			if(!vec_idhashequipasappm.contains(vecuser_idhashequipabd.elementAt(i))){
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

					String sql_delete = "delete from utilizadorhashequipa where id_hashequipa = '" + vecuser_idhashequipabd.elementAt(i) + "'";
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
}
