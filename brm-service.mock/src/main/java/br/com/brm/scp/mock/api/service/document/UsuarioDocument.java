package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;
import java.util.Collection;

import br.com.brm.scp.api.dto.response.GrupoPerfilResponse;

public class UsuarioDocument implements Serializable {

	private static final long serialVersionUID = 5905879916255485492L;

	private Long id;
	private String nome;
	private String cargo;
	private String email;
	private Collection<GrupoPerfilResponse> grupoPerfilResponse;


	public UsuarioDocument() {
		super();
	}

	public UsuarioDocument(Long id, String nome, String cargo, String email) {
		super();
		this.id = id;
		this.nome = nome;
		this.cargo = cargo;
		this.email = email;
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

	public void setNome(String name) {
		this.nome = name;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Collection<GrupoPerfilResponse> getGrupoPerfilResponse() {
		return grupoPerfilResponse;
	}

	public void setGrupoPerfilResponse(Collection<GrupoPerfilResponse> grupoPerfilResponse) {
		this.grupoPerfilResponse = grupoPerfilResponse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
