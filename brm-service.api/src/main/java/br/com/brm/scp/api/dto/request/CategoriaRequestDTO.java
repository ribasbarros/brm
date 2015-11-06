package br.com.brm.scp.api.dto.request;

import java.io.Serializable;
import java.util.Date;

public class CategoriaRequestDTO implements Serializable {

	private static final long serialVersionUID = -4794728247225374251L;
	
	private String id;
	private String nome;
	private Date dataCriacao;
	private Date dataAlteracao;
	
	
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
