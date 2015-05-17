package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;

public class TagDocument implements Serializable {

	private static final long serialVersionUID = -4079619566512672873L;

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
