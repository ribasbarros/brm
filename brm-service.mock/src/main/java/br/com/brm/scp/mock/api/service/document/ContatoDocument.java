package br.com.brm.scp.mock.api.service.document;

import java.util.Collection;

public class ContatoDocument {

	private Long id;
	private String endereco;
	private String cidade;
	private String uf;
	private String cep;
	private Collection<TelefoneDocument> telefone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Collection<TelefoneDocument> getTelefone() {
		return telefone;
	}

	public void setTelefone(Collection<TelefoneDocument> telefone) {
		this.telefone = telefone;
	}

}
