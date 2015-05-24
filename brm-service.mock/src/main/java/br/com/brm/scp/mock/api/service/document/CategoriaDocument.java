package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;

public class CategoriaDocument implements Serializable {

	private static final long serialVersionUID = 4818676017623732383L;
	
	private Long id;
	private String nome;

	
	public CategoriaDocument() {
		super();
	}

	public CategoriaDocument(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

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
