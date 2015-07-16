package br.com.brm.scp.api.dto.response;

import java.util.Date;

import br.com.brm.scp.api.annotation.BindingClassMeta;

import java.util.Collection;

public class UsuarioResponseDTO {

	private Long id;
	private String nome;
	private String cargo;
	private String email;
	@BindingClassMeta("GRUPOS")
	private Collection<GrupoResponseDTO> grupos;
	private Date dataExcluido;

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
