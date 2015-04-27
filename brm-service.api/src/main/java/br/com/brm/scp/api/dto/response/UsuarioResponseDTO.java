package br.com.brm.scp.api.dto.response;

import java.util.Date;
import java.util.Collection;

public class UsuarioResponseDTO {

	private Long id;
	private String nome;
	private String cargo;
	private String email;
	private Collection<GrupoPerfilResponse> grupoPerfilResponse;
	private Date dataDeletado;
	
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

	public Collection<GrupoPerfilResponse> getGrupoPerfilResponse() {
		return grupoPerfilResponse;
	}

	public void setGrupoPerfilResponse(Collection<GrupoPerfilResponse> grupoPerfilResponse) {
		this.grupoPerfilResponse = grupoPerfilResponse;
	}

	public Date getDataDeletado() {
		return dataDeletado;
	}

	public void setDataDeletado(Date dataDeletado) {
		this.dataDeletado = dataDeletado;
	}
}
