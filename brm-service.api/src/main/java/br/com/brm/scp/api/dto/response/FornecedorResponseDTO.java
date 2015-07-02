package br.com.brm.scp.api.dto.response;

import java.io.Serializable;
import java.util.Collection;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.ContatoDTO;


public class FornecedorResponseDTO implements Serializable {

	private static final long serialVersionUID = 6367545127710302291L;
	
	private Long id;
	private String razaoSocial;
	private String nomeFantasia;
	private String cnpj;
	private String inscricaoEstadual;
	private String descricao;
	
	@BindingClassMeta("CONTATO")
	private Collection<ContatoDTO> contato;

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
	
	
}
