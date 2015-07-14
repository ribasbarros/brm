package br.com.brm.scp.api.dto.request;

import java.util.Date;

public class CategoriaRequestDTO {

	private Long id;
	private String nome;
	private Date dataExcluido;
	
	public CategoriaRequestDTO() {
		super();
	}

	public CategoriaRequestDTO(Long id, String nome) {
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
	
	public Date getDataExcluido() {
		return dataExcluido;
	}

	public void setDataExcluido(Date dataExcluido) {
		this.dataExcluido = dataExcluido;
	}


}
