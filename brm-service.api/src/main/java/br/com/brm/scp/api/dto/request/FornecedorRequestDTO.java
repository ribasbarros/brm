package br.com.brm.scp.api.dto.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import br.com.brm.scp.api.annotation.BindingClassMeta;
import br.com.brm.scp.api.dto.ContatoDTO;
import br.com.brm.scp.api.dto.FornecedorCentroDTO;

public class FornecedorRequestDTO implements Serializable {

	private static final long serialVersionUID = -9082309408255141102L;

	private String id;
	private String razaoSocial;
	private String nomeFantasia;
	private String descricao;
	private String cnpj;

	@BindingClassMeta("CONTATO")
	private Collection<ContatoDTO> contatos = new ArrayList<>();

	@BindingClassMeta("CENTRO")
	private Collection<FornecedorCentroDTO> centros = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Collection<ContatoDTO> getContatos() {
		return contatos;
	}

	public void setContatos(Collection<ContatoDTO> contatos) {
		this.contatos = contatos;
	}

	public Collection<FornecedorCentroDTO> getCentros() {
		return centros;
	}

	public void setCentros(Collection<FornecedorCentroDTO> centros) {
		this.centros = centros;
	}

}