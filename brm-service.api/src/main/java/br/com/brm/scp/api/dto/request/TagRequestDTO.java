package br.com.brm.scp.api.dto.request;

import java.io.Serializable;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;

public class TagRequestDTO implements Serializable {

	
	private static final long serialVersionUID = 5917529930795039062L;
	
	private String id;
	private String nome;
	private Integer nivel;
	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioCriacao;

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
