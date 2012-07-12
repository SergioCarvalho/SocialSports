package indexacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class Preferencias {

	public static Vector<String> devolvePreferenciasBDUtilizador(int id_utilizador) {
		// TODO Auto-generated method stub
		Vector<String> vec_preferencias = new Vector<String>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			String sql = "select tipoinformacao,lingua,fonteinformacao from utilizadorpreferencias " +
					"where id_utilizador = '" + id_utilizador + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				vec_preferencias.add(rs.getString(1));
				vec_preferencias.add(rs.getString(2));
				vec_preferencias.add(rs.getString(3));
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vec_preferencias;
		
	}

	public static void ActualizaPreferenciasUtilizador(int id_utilizador, String tipo_informacao, 
			String lingua, String fontes_informacao) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/socialsports", "root", "mysqlsergio");
			Statement stmt = conn.createStatement();

			//actualiza os dados de preferencias do utilizador
			String sql = "UPDATE utilizadorpreferencias SET id_utilizador = '" + id_utilizador + "', tipoinformacao = '" + 
			tipo_informacao + "', lingua = '" + lingua + "', fonteinformacao = '" + fontes_informacao + "' WHERE id_preferencia=1";
		
			stmt.executeUpdate(sql);
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro na inserção das hashtags de equipa na tabela utilizadorhashequipa");
		}
		
	}

}
