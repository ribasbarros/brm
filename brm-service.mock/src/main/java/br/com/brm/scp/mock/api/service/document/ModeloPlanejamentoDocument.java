package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;

public class ModeloPlanejamentoDocument implements Serializable {

	private static final long serialVersionUID = 6767891844887720570L;
	
	private Long id;
	private String nome;
	private Integer meses;

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

	public Integer getMeses() {
		return meses;
	}

	public void setMeses(Integer meses) {
		this.meses = meses;
	}

}
