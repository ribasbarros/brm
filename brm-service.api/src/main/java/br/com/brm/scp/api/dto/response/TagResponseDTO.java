package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

public class TagResponseDTO implements Serializable {

	private static final long serialVersionUID = 4657331861203713237L;
	
	private Long id;
	private String nome;
	private Integer nivel;

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

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

}
