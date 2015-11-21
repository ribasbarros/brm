package br.com.brm.scp.api.dto.request;

import java.io.Serializable;

import br.com.brm.scp.api.service.status.OrigemTipoEnum;

public class OrigemSkuResponseDTO implements Serializable {

	private static final long serialVersionUID = 8887197402207141591L;

	private OrigemTipoEnum tipo;
	private String id;
	private boolean padrao;

	public OrigemTipoEnum getTipo() {
		return tipo;
	}

	public void setTipo(OrigemTipoEnum tipo) {
		this.tipo = tipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isPadrao() {
		return padrao;
	}

	public void setPadrao(boolean padrao) {
		this.padrao = padrao;
	}

}
