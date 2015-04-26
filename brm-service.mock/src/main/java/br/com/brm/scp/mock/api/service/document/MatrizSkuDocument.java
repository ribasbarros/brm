package br.com.brm.scp.mock.api.service.document;

import java.io.Serializable;

public class MatrizSkuDocument implements Serializable {

	private static final long serialVersionUID = -3195603003388449958L;
	
	private Long id;
	private Long idDfu;
	private Long idSku;
	private Double representa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdDfu() {
		return idDfu;
	}

	public void setIdDfu(Long idDfu) {
		this.idDfu = idDfu;
	}

	public Long getIdSku() {
		return idSku;
	}

	public void setIdSku(Long idSku) {
		this.idSku = idSku;
	}

	public Double getRepresenta() {
		return representa;
	}

	public void setRepresenta(Double representa) {
		this.representa = representa;
	}

}
