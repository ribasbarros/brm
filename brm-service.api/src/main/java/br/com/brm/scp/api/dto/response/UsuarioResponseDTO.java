package br.com.brm.scp.api.dto.response;

import java.io.Serializable;
import java.util.Date;

import br.com.brm.scp.api.annotation.BindingClassMeta;

import java.util.Collection;

public class UsuarioResponseDTO implements Serializable{

	private static final long serialVersionUID = -6583154136515769341L;
	private String id;
	private String nome;
	private String cargo;
	private String email;
	@BindingClassMeta("GRUPOS")
	private Collection<GrupoResponseDTO> grupos;
	private Date dataExcluido;

	

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

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<GrupoResponseDTO> getGrupos() {
		return grupos;
	}

	public void setGrupos(Collection<GrupoResponseDTO> grupos) {
		this.grupos = grupos;
	}

	public Date getDataExcluido() {
		return dataExcluido;
	}

	public void setDataExcluido(Date dataExcluido) {
		this.dataExcluido = dataExcluido;
	}
}
