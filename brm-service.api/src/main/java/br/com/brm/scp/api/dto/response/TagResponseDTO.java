package br.com.brm.scp.api.dto.response;

import java.io.Serializable;
import java.util.Date;

import br.com.brm.scp.api.annotation.BindingClassMeta;

public class TagResponseDTO implements Serializable {

	private static final long serialVersionUID = 4657331861203713237L;
	
	private String id;
	private String nome;
	private Integer nivel;
	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioCriacao;
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

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public UsuarioResponseDTO getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(UsuarioResponseDTO usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	
	

}
