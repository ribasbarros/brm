package br.com.brm.scp.api.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import br.com.brm.scp.api.annotation.BindingClassMeta;

public class GrupoResponseDTO implements Serializable {

	private static final long serialVersionUID = 3466402864890748L;
	
	private String id;
	private String nome;

	@BindingClassMeta("PERFIS")
	private Collection<PerfilResponseDTO> perfis = new ArrayList<>();
	@BindingClassMeta("USUARIO")
	private UsuarioResponseDTO usuarioCriacao;
	private Date dataCriacao;
	private Date dataAlteracao;

	
	
	public GrupoResponseDTO() {
		super();
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

	public Collection<PerfilResponseDTO> getPerfis() {
		return perfis;
	}

	public void setPerfis(Collection<PerfilResponseDTO> perfis) {
		this.perfis = perfis;
	}
	
	public UsuarioResponseDTO getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(UsuarioResponseDTO usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	
	

}
