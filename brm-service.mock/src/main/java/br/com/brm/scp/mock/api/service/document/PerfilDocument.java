package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;
import java.util.Date;

public class PerfilDocument implements Serializable {

	private static final long serialVersionUID = 8729468500938963438L;
	
	public Long id;
	private String nome;
	private Date dataExcluido;


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

	public Date getDataExcluido() {
		return dataExcluido;
	}

	public void setDataExcluido(Date dataExcluido) {
		this.dataExcluido = dataExcluido;
	}
	
}
