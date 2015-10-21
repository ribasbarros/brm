package br.com.brm.scp.api.service.document;

import java.io.Serializable;

import br.com.brm.scp.api.dto.TelefoneDTO;
import br.com.brm.scp.fw.annotations.BindingClass;

public class ContatoDocument implements Serializable {

	private static final long serialVersionUID = -6433003551096069983L;

	private String nome;

	@BindingClass(TelefoneDTO.class)
	private TelefoneDocument telefone;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TelefoneDocument getTelefone() {
		return telefone;
	}

	public void setTelefone(TelefoneDocument telefone) {
		this.telefone = telefone;
	}

}
