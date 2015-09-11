package br.com.brm.scp.api.dto.request;

import java.io.Serializable;
import java.util.Date;

public class PerfilRequestDTO implements Serializable {

	private static final long serialVersionUID = -551282430682709369L;
	
	public String id;
	private String nome;

		
	public PerfilRequestDTO(String id, String nome) {
		super();
		this.id = id;
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
