package br.com.brm.scp.api.dto.response;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import br.com.brm.scp.api.annotation.BindingClassMeta;

public class GrupoResponseDTO implements Serializable {

	private static final long serialVersionUID = 3466402864890748L;
	
	private Long id;
	private String nome;
	@BindingClassMeta("PERFIS")
	private Collection<PerfilResponseDTO> perfis;
	private Date dataExcluido;
	
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

	public Collection<PerfilResponseDTO> getPerfis() {
		return perfis;
	}

	public void setPerfis(Collection<PerfilResponseDTO> perfis) {
		this.perfis = perfis;
	}

	public Date getDataExcluido() {
		return dataExcluido;
	}

	public void setDataExcluido(Date dataExcluido) {
		this.dataExcluido = dataExcluido;
	}

}
