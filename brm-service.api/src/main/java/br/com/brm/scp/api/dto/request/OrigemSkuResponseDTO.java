package br.com.brm.scp.api.dto.request;

import java.io.Serializable;

import br.com.brm.scp.mock.api.service.status.OrigemTipoEnum;

public class OrigemSkuResponseDTO implements Serializable {

	private static final long serialVersionUID = 8887197402207141591L;

	private OrigemTipoEnum tipo;
	private String id;
	private Boolean isDefault;

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

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

}
