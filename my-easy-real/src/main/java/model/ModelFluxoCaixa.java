package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ModelFluxoCaixa implements Serializable {
	
	private static final long serialVersionUID =1L;
	
	private Long id;
	private LocalDate dataMovimento;
	private String descricao;
	private BigDecimal valorMovimento; 
	private char tipoMovimento;
	private Long usuarioPaiId;
	
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDataMovimento() {
		return dataMovimento;
	}
	public void setDataMovimento(LocalDate dataMovimento) {
		this.dataMovimento = dataMovimento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getValorMovimento() {
		return valorMovimento;
	}
	
	public void setValorMovimento(BigDecimal valorMovimento) {
		if(this.tipoMovimento =='S') {
			this.valorMovimento = valorMovimento.negate();
		}else {
			this.valorMovimento = valorMovimento;
		}
	}
	
	public char getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(char tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	
	public Long getUsuarioPaiId() {
		return usuarioPaiId;
	}
	public void setUsuarioPaiId(Long usuarioPaiId) {
		this.usuarioPaiId = usuarioPaiId;
	}
	
}
