package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.ConnectionBanco;
import model.ModelUsuario;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = ConnectionBanco.getConnection();
	}

	public String confirmarCadastro(String token) throws SQLException {
		String sql = "SELECT id, confirmado FROM usuarios WHERE token_confirmacao = ? AND data_expiracao > now()";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, token);

			try (ResultSet resultSet = statement.executeQuery()) {
				String msg;

				if (resultSet.next()) {
					if (resultSet.getBoolean("confirmado")) {
						msg = "Cadastro já confirmado anteriormente";
					} else {
						int idUsuario = resultSet.getInt("id");
						String updateSql = "UPDATE usuarios SET confirmado = true WHERE id = ?";
						PreparedStatement updateStatement = connection.prepareStatement(updateSql);
						updateStatement.setInt(1, idUsuario);
						updateStatement.executeUpdate();
						connection.commit();

						msg = "Cadastro confirmado com sucesso!";

					}
				} else {
					msg = "token inválido ou expirado. Tente se cadastrar novamente";
				}

				return msg;
			}
		}

	}

	public boolean cadastrarUsuario(ModelUsuario usuario) throws SQLException {
		String sql = "INSERT INTO usuarios(nome, email, senha, token_confirmacao, data_expiracao) VALUES (?,?,?,?,?)";

		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getEmail());
			statement.setString(3, usuario.getSenha());
			statement.setString(4, usuario.getTokenConfirmacao());
			statement.setTimestamp(5, usuario.getDataExpiracao());

			statement.execute();
			connection.commit();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}

			return false;
		}

	}

	public boolean editarFotoUser(ModelUsuario usuario) throws SQLException {
		
		if( usuario == null ||
			usuario.getId() == null ||
			usuario.getFotoUser() == null ||
			usuario.getExtensaoFotoUser() == null ) {
			
			throw new IllegalArgumentException("Dados do usuário incompletos para atualização de foto.");
		}
		
		String sql = "UPDATE public.usuarios SET foto_user=?, extensao_foto_user=? WHERE  id = ?;";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, usuario.getFotoUser());
			statement.setString(2, usuario.getExtensaoFotoUser());
			statement.setLong(3, usuario.getId());
			
			int linhasAfetadas = statement.executeUpdate();
			connection.commit();
			
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}

			return false;
		}

	}

	public ModelUsuario consultarUsuarioLogado(String email) throws Exception {
		
		ModelUsuario modelUsuario = new ModelUsuario();

		String sql = "SELECT * FROM usuarios WHERE upper(email) = upper(?);";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, email);

			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					modelUsuario.setId(result.getLong("id"));
					modelUsuario.setNome(result.getString("nome"));
					modelUsuario.setEmail(result.getString("email"));
					modelUsuario.setSenha(result.getString("senha"));
					modelUsuario.setUsuario_confirmado(result.getBoolean("confirmado"));
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
		}

		return modelUsuario;

	}

	public ModelUsuario consultarUsuarioById(Long idUsuarioLogado) throws Exception {
		ModelUsuario modelUsuario = new ModelUsuario();

		String sql = "SELECT * FROM usuarios WHERE upper(email) = upper(?);";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, idUsuarioLogado);

			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					modelUsuario.setId(result.getLong("id"));
					modelUsuario.setNome(result.getString("nome"));
					modelUsuario.setEmail(result.getString("email"));
					modelUsuario.setSenha(result.getString("senha"));
					modelUsuario.setFotoUser(result.getString("foto_user"));
					modelUsuario.setExtensaoFotoUser(result.getString("extensao_foto_user"));

				} else {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
		}

		return modelUsuario;

	}

}
