package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ModelUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String tokenConfirmacao;
	private Timestamp dataExpiracao;
	private boolean usuario_confirmado;
	private String fotoUser;
	private String extensaoFotoUser;
	
	

	private List<ModelFluxoCaixa> fluxoCaixa = new ArrayList<ModelFluxoCaixa>();

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {

		this.senha = senha;
	}

	public String getTokenConfirmacao() {
		return tokenConfirmacao;
	}

	public void setTokenConfirmacao(String tokenConfirmacao) {
		this.tokenConfirmacao = tokenConfirmacao;
	}

	public Timestamp getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(Timestamp dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public boolean isUsuario_confirmado() {
		return usuario_confirmado;
	}

	public void setUsuario_confirmado(boolean usuario_confirmado) {
		this.usuario_confirmado = usuario_confirmado;
	}
	
	public String getFotoUser() {
		return fotoUser;
	}

	public void setFotoUser(String fotoUser) {
		this.fotoUser = fotoUser;
	}

	public String getExtensaoFotoUser() {
		return extensaoFotoUser;
	}

	public void setExtensaoFotoUser(String extensaoFotoUser) {
		this.extensaoFotoUser = extensaoFotoUser;
	}


	public List<ModelFluxoCaixa> getFluxoCaixa() {
		return fluxoCaixa;
	}

	public void setFluxoCaixa(List<ModelFluxoCaixa> fluxoCaixa) {
		this.fluxoCaixa = fluxoCaixa;
	}

	
	@Override
	public String toString() {
		return "ModelUsuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha
				+ ", tokenConfirmacao=" + tokenConfirmacao + ", dataExpiracao=" + dataExpiracao
				+ ", usuario_confirmado=" + usuario_confirmado + "]";
	}

}
