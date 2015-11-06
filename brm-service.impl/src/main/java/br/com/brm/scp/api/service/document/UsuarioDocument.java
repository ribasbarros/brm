package br.com.brm.scp.api.service.document;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.brm.scp.api.dto.response.GrupoResponseDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
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
	@DBRef
	@BindingClass(UsuarioResponseDTO.class)
	private UsuarioDocument usuarioCriacao;
	private String login;
	private String senha;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataCriacao;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date dataAlteracao;


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
	
	public UsuarioDocument getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(UsuarioDocument usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}


}
