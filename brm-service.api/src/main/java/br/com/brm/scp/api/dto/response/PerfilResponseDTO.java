package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

public class PerfilResponseDTO implements Serializable {

	private static final long serialVersionUID = -551282430682709369L;
	
	public Long id;
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
