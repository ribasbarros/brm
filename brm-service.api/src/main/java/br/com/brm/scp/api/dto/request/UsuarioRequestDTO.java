package br.com.brm.scp.api.dto.request;

import java.util.Date;
import java.io.Serializable;
import java.util.Collection;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.response.GrupoResponseDTO;

public class UsuarioRequestDTO implements Serializable {

	private static final long serialVersionUID = -494285582579032286L;
	
	private Long id;
	private String nome;
	private String cargo;
	private String email;
	@BindingClassMeta("GRUPOS")
	private Collection<GrupoResponseDTO> grupos;
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

	public Collection<GrupoResponseDTO> getGrupos() {
		return grupos;
	}

	public void setGrupos(Collection<GrupoResponseDTO> grupos) {
		this.grupos = grupos;
	}

	public Date getDataDeletado() {
		return dataDeletado;
	}

	public void setDataDeletado(Date dataDeletado) {
		this.dataDeletado = dataDeletado;
	}
	
}
