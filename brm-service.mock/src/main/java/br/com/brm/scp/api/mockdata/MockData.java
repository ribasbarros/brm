package br.com.brm.scp.api.mockdata;

import java.util.HashMap;
import java.util.Map;

import br.com.brm.scp.api.service.document.UsuarioDocument;

public class MockData {
	
	private String dateFormat = "ddMMyyyy";

	private static final Map<Long, UsuarioDocument> USUARIO_COLLECTION = new HashMap<Long, UsuarioDocument>();

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Map<Long, UsuarioDocument> getUsuarioCollection() {
		return USUARIO_COLLECTION;
	}

}
