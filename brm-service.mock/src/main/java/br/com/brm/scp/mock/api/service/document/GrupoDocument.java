package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import br.com.brm.scp.api.annotation.BindingClassMeta;

public class GrupoDocument implements Serializable {

	private static final long serialVersionUID = -2946370998935414082L;
	
	private Long id;
	private String nome;
	@BindingClassMeta("PERFIS")
	private Collection<PerfilDocument> perfis;
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

	public Collection<PerfilDocument> getPerfis() {
		return perfis;
	}

	public void setPerfis(Collection<PerfilDocument> perfis) {
		this.perfis = perfis;
	}

	public Date getDataExcluido() {
		return dataExcluido;
	}

	public void setDataExcluido(Date dataExcluido) {
		this.dataExcluido = dataExcluido;
	}
		
}
