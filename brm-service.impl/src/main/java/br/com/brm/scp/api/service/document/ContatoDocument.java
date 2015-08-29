package br.com.brm.scp.api.service.document;

import java.io.Serializable;
import java.util.Collection;

import br.com.brm.scp.api.dto.TelefoneDTO;
import br.com.brm.scp.fw.annotations.BindingClass;

public class ContatoDocument implements Serializable {

	private static final long serialVersionUID = -6433003551096069983L;

	private String nome;
	
	@BindingClass(TelefoneDTO.class)
	private Collection<TelefoneDocument> telefone;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Collection<TelefoneDocument> getTelefone() {
		return telefone;
	}

	public void setTelefone(Collection<TelefoneDocument> telefone) {
		this.telefone = telefone;
	}

}
