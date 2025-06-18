package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionBanco;
import model.ModelFluxoCaixa;

public class DAOFluxoCaixaRepository {
	
	private Connection connection;
	
	
	
	public DAOFluxoCaixaRepository() {
		connection = ConnectionBanco.getConnection();
	}
	
	
	
	public ModelFluxoCaixa registrarMovimentacao(ModelFluxoCaixa modelFluxoCaixa) {
		
		String sql = "INSERT INTO public.fluxo_caixa(data_movimento, descricao, valor_movimento, tipo_movimento, usuario_pai_id) VALUES (?, ?, ?, ?, ?) RETURNING id;";
		long idMovimentacao = -1 ;
		
		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setDate(1, Date.valueOf(modelFluxoCaixa.getDataMovimento()));
			statement.setString(2, modelFluxoCaixa.getDescricao());
			statement.setBigDecimal(3, modelFluxoCaixa.getValorMovimento());
			statement.setString(4, String.valueOf(modelFluxoCaixa.getTipoMovimento()));
			statement.setLong(5, modelFluxoCaixa.getUsuarioPaiId());
			
			try(ResultSet result = statement.executeQuery()){
				connection.commit();
				if(result.next()) {
					 idMovimentacao = result.getLong("id");
				}
				
			}
				
		}catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            connection.rollback();
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	         
	    }
		
		if(idMovimentacao!=-1) {
			return this.consultarMovimentacao(idMovimentacao, modelFluxoCaixa.getUsuarioPaiId());
		}
		
		return null;
	
	}
	
	public void editarMovimentacao(ModelFluxoCaixa modelFluxoCaixa) {
		String sql = "UPDATE fluxo_caixa SET  data_movimento=?, descricao=?, valor_movimento=?, tipo_movimento=?, usuario_pai_id=? WHERE id=?;";
		
		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setDate(1, Date.valueOf(modelFluxoCaixa.getDataMovimento()));
			statement.setString(2, modelFluxoCaixa.getDescricao());
			statement.setBigDecimal(3, modelFluxoCaixa.getValorMovimento());
			statement.setString(4, String.valueOf(modelFluxoCaixa.getTipoMovimento()));
			statement.setLong(5, modelFluxoCaixa.getUsuarioPaiId());
			statement.setLong(6, modelFluxoCaixa.getId());
			
			statement.executeUpdate();
			connection.commit();
		}catch (Exception e) {
			e.printStackTrace();
	        try {
	            connection.rollback();
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
		}
	
	}	
	
	public void deletarMovimentacao(Long idMovimentacao) {
		String sql = "DELETE FROM fluxo_caixa WHERE id = ?;";
		try (PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setLong(1, idMovimentacao);
			statement.executeUpdate();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
	        try {
	            connection.rollback();
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
		}
		

	}
	
	//essa consulta com retorno é pra ser utilizado quando for editar 
	public ModelFluxoCaixa consultarMovimentacao(Long idMovimentacao, Long userLogado) {
		ModelFluxoCaixa modelFluxoCaixa = new ModelFluxoCaixa();
		
		String sql = "SELECT * FROM  fluxo_caixa WHERE  usuario_pai_id = ? AND id= ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setLong(1, userLogado);
			statement.setLong(2, idMovimentacao);
			
			try(ResultSet result = statement.executeQuery()) {
				if(result.next()) {
					modelFluxoCaixa.setId(result.getLong("id"));
					modelFluxoCaixa.setDataMovimento(result.getDate("data_movimento").toLocalDate());
					modelFluxoCaixa.setDescricao(result.getString("descricao"));
					modelFluxoCaixa.setValorMovimento(result.getBigDecimal("valor_movimento"));
					modelFluxoCaixa.setTipoMovimento(result.getString("tipo_movimento").charAt(0));
					modelFluxoCaixa.setUsuarioPaiId(result.getLong("usuario_pai_id"));
					return modelFluxoCaixa;
				}
			}
				
		} catch (Exception e) {
			e.printStackTrace();  
		}
		
		return null;
	}
	
	
	
	
	public List<ModelFluxoCaixa> carregarMovimentacoes(Long idUserLogado, int mesSql, int ano ){
		List<ModelFluxoCaixa> retornoFluxoCaixa = new ArrayList<ModelFluxoCaixa>();
		String sql = "SELECT * FROM fluxo_caixa WHERE usuario_pai_id = ? AND EXTRACT(MONTH FROM data_movimento) = ? AND EXTRACT(YEAR FROM data_movimento) = ?";
		try(PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, idUserLogado);
			statement.setInt(2, mesSql);
			statement.setInt(3, ano);
			
			try(ResultSet result = statement.executeQuery()){
				while(result.next()) {
					ModelFluxoCaixa modelFluxoCaixa = new ModelFluxoCaixa();
					modelFluxoCaixa.setId(result.getLong("id"));
					modelFluxoCaixa.setDataMovimento(result.getDate("data_movimento").toLocalDate());
					modelFluxoCaixa.setDescricao(result.getString("descricao"));
					modelFluxoCaixa.setValorMovimento(result.getBigDecimal("valor_movimento"));
					modelFluxoCaixa.setTipoMovimento(result.getString("tipo_movimento").charAt(0));
					modelFluxoCaixa.setUsuarioPaiId(result.getLong("usuario_pai_id"));
					retornoFluxoCaixa.add(modelFluxoCaixa);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		

		return retornoFluxoCaixa;
	}

}
