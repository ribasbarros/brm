package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;

//TODO REANALISAR
public class PacotePlanoDocument implements Serializable {

	private static final long serialVersionUID = 472787030476905806L;
	
	private Long id;
	private String nome;

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

}
