package connection;

import java.sql.Connection;
import java.sql.DriverManager;
/* Essa classe criar uma nova conexão com o banco a cada chamada do getConnection()
para utilizar o try-with-resources  com segurança*/

public class ConnectionBanco {
	private static String urlBanco = "jdbc:postgresql://localhost:5432/my_easy_real?autoReconnect=true";;
	private static String user = "postgres";
	private static String password ="admin";
	
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao carregar o driver do banco de dados"+
											e.getMessage());
		}
	}
	
	
	public static Connection getConnection() {
		try {
			Connection connection = DriverManager.getConnection(urlBanco,user,password);
			connection.setAutoCommit(false);
			return connection;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao obter conexão com o banco"+e.getMessage());
		}
	}
	
}
