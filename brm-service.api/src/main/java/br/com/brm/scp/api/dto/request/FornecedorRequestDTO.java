package br.com.brm.scp.api.dto.request;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.ContatoDTO;

public class FornecedorRequestDTO implements Serializable {
	
	private static final long serialVersionUID = 4415195505536182040L;
	
	private Long id;
	private String razaoSocial;
	private String nomeFantasia;
	private String cnpj;
	private String inscricaoEstadual;
	private String descricao;
	private Date dataExcluido;
	
	@BindingClassMeta("CONTATO")
	private Collection<ContatoDTO> contato;
	
	public FornecedorRequestDTO() {
		super();
	}

	public FornecedorRequestDTO(Long id, String razaoSocial, String nomeFantasia, String cnpj, String inscricaoEstadual,
			String descricao, Collection<ContatoDTO> contato) {
		super();
		this.id = id;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.cnpj = cnpj;
		this.inscricaoEstadual = inscricaoEstadual;
		this.descricao = descricao;
		this.contato = contato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Collection<ContatoDTO> getContato() {
		return contato;
	}

	public void setContato(Collection<ContatoDTO> contato) {
		this.contato = contato;
	}

	public Date getDataExcluido() {
		return dataExcluido;
	}

	public void setDataExcluido(Date dataExcluido) {
		this.dataExcluido = dataExcluido;
	}	
	
}
