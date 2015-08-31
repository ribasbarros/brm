package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

public class CategoriaResponseDTO implements Serializable{

	private static final long serialVersionUID = 6915785791468490870L;
	
	private String id;
	private String nome;

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
