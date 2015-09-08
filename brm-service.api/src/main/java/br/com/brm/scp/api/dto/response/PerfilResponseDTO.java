package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

public class PerfilResponseDTO implements Serializable {

	private static final long serialVersionUID = -551282430682709369L;
	
	public String id;
	private String nome;

	public PerfilResponseDTO() {
	}
	
	public PerfilResponseDTO(String id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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
