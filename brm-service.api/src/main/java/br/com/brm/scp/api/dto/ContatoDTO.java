package br.com.brm.scp.api.dto;

import java.io.Serializable;

import br.com.brm.scp.api.annotation.BindingClassMeta;

public class ContatoDTO implements Serializable {

	private static final long serialVersionUID = -3695238646035667057L;

	private String nome;

	@BindingClassMeta("TELEFONE")
	private TelefoneDTO telefone;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TelefoneDTO getTelefone() {
		return telefone;
	}

	public void setTelefone(TelefoneDTO telefone) {
		this.telefone = telefone;
	}

}
