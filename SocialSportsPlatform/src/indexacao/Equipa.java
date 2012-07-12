package indexacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Equipa {

	//método que devolve todos os nomes de equipas existentes na BD
	public static Vector<String> devolveEquipasBD(){

		Vector<String> vector_equipas = new Vector<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select nome_equipa from equipa";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vector_equipas.add(rs.getString(1));
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vector_equipas;
	}

	public static void insereEquipaBD(String equipa, String modalidade) {
		// TODO Auto-generated method stub
		int id_modalidade = devolveEquipaIdModalidade(modalidade);
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			
			String sql_insert = "insert into equipa (id_equipa, nome_equipa, id_modalidade)" +
			"values (null,'" + equipa + "','" + id_modalidade + "')";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_insert);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//método que devolve o id_modalidade associado a cada modalidade
	protected static int devolveEquipaIdModalidade(String modalidade) {
		// TODO Auto-generated method stub
		if(modalidade.equals("Futebol")){
			return 1;
		}
		else if(modalidade.equals("Ténis")){
			return 2;
		}

		else if(modalidade.equals("Basquetebol")){
			return 3;
		}
		else 
			return 1;
	}

	//devolve o id da equipa baseado no nome da equipa
	protected static int devolveEquipaIdEquipa(String equipa) {
		// TODO Auto-generated method stub
		int id_equipa = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_equipa from equipa where nome_equipa = '" + equipa + "'";
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
}
