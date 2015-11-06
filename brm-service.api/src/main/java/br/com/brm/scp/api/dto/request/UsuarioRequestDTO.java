package br.com.brm.scp.api.dto.request;

import java.util.Date;
import java.io.Serializable;
import java.util.Collection;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.response.GrupoResponseDTO;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;

public class UsuarioRequestDTO implements Serializable {

	private static final long serialVersionUID = -494285582579032286L;
	
	private String id;
	private String nome;
	private String cargo;
	private String email;
	@BindingClassMeta("GRUPOS")
	private Collection<GrupoResponseDTO> grupos;
	private Date dataExcluido;
	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioCriacao;
	private String login;
	private String senha;
	private Date dataCriacao;
	private Date dataAlteracao;
	
	
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
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
	public UsuarioResponseDTO getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(UsuarioResponseDTO usuarioCriacao) {
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
	
	
		
}
