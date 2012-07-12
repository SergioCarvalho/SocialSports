package indexacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Jogador {

	public static Vector<String> devolveJogadoresBD() {
		// TODO Auto-generated method stub
		Vector<String> vector_jogadores = new Vector<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select nome_jogador from jogador";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vector_jogadores.add(rs.getString(1));
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vector_jogadores;
	}

	public static void insereJogadorBD(String jogador, String modalidade) {
		// TODO Auto-generated method stub
		int id_modalidade = devolveJogadorIdModalidade(modalidade);

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");

			String sql_insert = "insert into jogador (id_jogador, nome_jogador, id_modalidade)" +
			"values (null,'" + jogador + "','" + id_modalidade + "')";
			Statement stmt_insert = conn.createStatement();
			stmt_insert.executeUpdate(sql_insert);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//devolve o id da modalidade baseado no nome da modalidade
	protected static int devolveJogadorIdModalidade(String modalidade) {
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

	//devolve o id do jogador baseado no nome do jogador
	public static int devolveJogadorIdJogador(String jogador) {
		// TODO Auto-generated method stub
		int id_jogador = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select id_jogador from jogador where nome_jogador = '" + jogador + "'";
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

}
