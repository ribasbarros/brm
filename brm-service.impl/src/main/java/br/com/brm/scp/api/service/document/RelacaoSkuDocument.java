package br.com.brm.scp.api.service.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
//TODO RIBAS REVER ESSA PORRA QUE TA ERRADA
@Document
public class RelacaoSkuDocument implements Serializable{	
	private static final long serialVersionUID = -6690751074830703188L;
	
	@Id
	private String id;
	
	@DBRef
	private String idDfu;
	
	@DBRef
	private String idSku;
	
	private Double representa;

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

	public Double getRepresenta() {
		return representa;
	}

	public void setRepresenta(Double representa) {
		this.representa = representa;
	}
		
}
