package br.com.brm.scp.api.dto.response;

import java.io.Serializable;
import java.util.Date;

public class PerfilResponseDTO implements Serializable {

	private static final long serialVersionUID = -551282430682709369L;
	
	public Long id;
	private String nome;
	private Date dataExcluido;

	public PerfilResponseDTO() {
	}
	
	public PerfilResponseDTO(Long id, String nome, Date dataExcluido) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataExcluido = dataExcluido;
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
