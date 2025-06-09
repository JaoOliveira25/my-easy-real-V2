package connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
/* Essa classe criar uma nova conexão com o banco a cada chamada do getConnection()
para utilizar o try-with-resources  com segurança*/

public class ConnectionBanco {
	
	private static String urlBanco ;
	private static String user ;
	private static String password ;
	
	
	
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
			
			Properties props = new Properties();
			InputStream input = ConnectionBanco.class.getClassLoader().getResourceAsStream("config.properties");
			
			if(input==null) {
				throw new RuntimeException("Arquivo config.properties não encontrado no classpath.");
			}
			
			props.load(input);
			
			urlBanco = props.getProperty("db.url");
			user = props.getProperty("db.user");
			password = props.getProperty("db.password");
			
			
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
