package br.com.brm.scp.api.dto.response;

import java.io.Serializable;
import java.util.Date;

import br.com.brm.scp.api.annotation.BindingClassMeta;

public class PerfilResponseDTO implements Serializable {

	private static final long serialVersionUID = -551282430682709369L;
	
	public String id;
	private String nome;
	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioCriacao;
	private Date dataCriacao;
	private Date dataAlteracao;

	public PerfilResponseDTO() {
	}
	
	public PerfilResponseDTO(String id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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
	public UsuarioResponseDTO getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(UsuarioResponseDTO usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	
		
}
