package br.com.brm.scp.api.dto.request;

import java.io.Serializable;
import java.util.Date;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.response.UsuarioResponseDTO;

public class PerfilRequestDTO implements Serializable {

	private static final long serialVersionUID = -551282430682709369L;
	
	public String id;
	private String nome;
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

		
	public PerfilRequestDTO(String id, String nome) {
		super();
		this.id = id;
	}
	
	

	public PerfilRequestDTO() {
		super();
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
