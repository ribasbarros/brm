package br.com.brm.scp.api.service.document;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.brm.scp.api.dto.response.PerfilResponseDTO;
import br.com.brm.scp.fw.annotations.BindingClass;

@Document
public class GrupoDocument implements Serializable {

	private static final long serialVersionUID = -2946370998935414082L;
	@Id
	private String id;
	private String nome;
	@DBRef
	@BindingClass(PerfilResponseDTO.class)
	private Collection<PerfilDocument> perfis;

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

	public Collection<PerfilDocument> getPerfis() {
		return perfis;
	}

	public void setPerfis(Collection<PerfilDocument> perfis) {
		this.perfis = perfis;
	}
}