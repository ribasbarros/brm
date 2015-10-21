package br.com.brm.scp.api.dto;

import java.io.Serializable;


public class RelacionamentoSkuDTO implements Serializable{
	
	private static final long serialVersionUID = 7005405177794282663L;
	private String id;
	private String idDfu;
	private String idSku;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdDfu() {
		return idDfu;
	}
	public void setIdDfu(String idDfu) {
		this.idDfu = idDfu;
	}
	public String getIdSku() {
		return idSku;
	}
	public void setIdSku(String idSku) {
		this.idSku = idSku;
	}
}
