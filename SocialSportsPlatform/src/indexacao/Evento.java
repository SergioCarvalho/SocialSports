package indexacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Evento {
	
	public static Vector<String> devolveEventosBD() {
		// TODO Auto-generated method stub
		Vector<String> vector_eventos = new Vector<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select nome_evento from evento";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vector_eventos.add(rs.getString(1));
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vector_eventos;
	}

	public static void insereEventoBD(String evento, String modalidade) {
		// TODO Auto-generated method stub
		int id_modalidade = devolveEventoIdModalidade(modalidade);
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			
			String sql_insert = "insert into evento (id_evento, nome_evento, id_modalidade)" +
			"values (null,'" + evento + "','" + id_modalidade + "')";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_insert);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//método que devolve o id_modalidade associado a cada modalidade
	protected static int devolveEventoIdModalidade(String modalidade) {
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

	//devolve o id do evento baseado no nome do evento
	public static int devolveEventoIdEvento(String evento) {
		// TODO Auto-generated method stub
		int id_evento = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_evento from evento where nome_evento = '" + evento + "'";
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

}
