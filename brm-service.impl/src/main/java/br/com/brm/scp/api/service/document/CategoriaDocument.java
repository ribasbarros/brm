package br.com.brm.scp.api.service.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;
import br.com.brm.scp.fw.annotations.BindingClass;

@Document
public class CategoriaDocument implements Serializable {

	private static final long serialVersionUID = 4818676017623732383L;
	@Id
	private String id;
	private String nome;
	@DBRef
	@BindingClass(UsuarioResponseDTO.class)
	private UsuarioDocument usuarioCriacao;
	
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
	public UsuarioDocument getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(UsuarioDocument usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

}
