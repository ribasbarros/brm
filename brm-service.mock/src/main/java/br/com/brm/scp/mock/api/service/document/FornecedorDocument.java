package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;
import java.util.Collection;

import br.com.brm.scp.api.dto.ContatoDTO;
import br.com.brm.scp.fw.annotations.BindingClass;

public class FornecedorDocument implements Serializable {

	private static final long serialVersionUID = -9082309408255141102L;
	
	private Long id;
	private String razaoSocial;
	private String nomeFantasia;
	private String cnpj;
	private String inscricaoEstadual;
	private String descricao;
	
	@BindingClass(ContatoDTO.class)
	private Collection<ContatoDocument> contato;

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

	public Collection<ContatoDocument> getContato() {
		return contato;
	}

	public void setContato(Collection<ContatoDocument> contato) {
		this.contato = contato;
	}
	
	
}
