package br.com.brm.scp.api.service.document;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.brm.scp.api.dto.response.GrupoResponseDTO;
import br.com.brm.scp.fw.annotations.BindingClass;

@Document
public class UsuarioDocument implements Serializable {

	private static final long serialVersionUID = 5905879916255485492L;

	@Id
	private String id;
	private String nome;
	private String cargo;
	private String email;
	@DBRef
	@BindingClass(GrupoResponseDTO.class)
	private Collection<GrupoDocument> grupos;
	private Date dataExcluido;


	public UsuarioDocument() {
		super();
	}

	public UsuarioDocument(String id, String nome, String cargo, String email) {
		super();
		this.id = id;
		this.nome = nome;
		this.cargo = cargo;
		this.email = email;
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

	public void setNome(String name) {
		this.nome = name;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Collection<GrupoDocument> getGrupos() {
		return grupos;
	}

	public void setGrupos(Collection<GrupoDocument> grupos) {
		this.grupos = grupos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataExcluido() {
		return dataExcluido;
	}

	public void setDataExcluido(Date dataExcluido) {
		this.dataExcluido = dataExcluido;
	}
}
