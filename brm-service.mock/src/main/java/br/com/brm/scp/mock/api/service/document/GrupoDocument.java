package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;
import java.util.Collection;

public class GrupoDocument implements Serializable {

	private static final long serialVersionUID = -2946370998935414082L;
	
	private Long id;
	private String nome;
	private Collection<PerfilDocument> perfis;

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

	public Collection<PerfilDocument> getPerfis() {
		return perfis;
	}

	public void setPerfis(Collection<PerfilDocument> perfis) {
		this.perfis = perfis;
	}

}
