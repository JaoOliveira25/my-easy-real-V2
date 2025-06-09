package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import connection.ConnectionBanco;
import model.ModelUsuario;

public class DAOLoginRepository {
	private Connection connection;

	public DAOLoginRepository() {
		this.connection = ConnectionBanco.getConnection();
	}

	public boolean validarAutenticacao(ModelUsuario modelLogin) throws SQLException {
		String sql = "SELECT * FROM usuarios WHERE upper(email)= upper(?);";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, modelLogin.getEmail());

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String senhaHashBanco = resultSet.getString("senha");
					return BCrypt.checkpw(modelLogin.getSenha(), senhaHashBanco);
					// true senha correta false senha incorreta
				}

			}

		}
		return false;

	}

}
