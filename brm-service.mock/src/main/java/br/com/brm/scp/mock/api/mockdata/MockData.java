package br.com.brm.scp.mock.api.mockdata;

import java.util.HashMap;
import java.util.Map;

import br.com.brm.scp.mock.api.service.document.TagDocument;
import br.com.brm.scp.mock.api.service.document.UsuarioDocument;

public class MockData {
	
	private String dateFormat = "ddMMyyyy";

	private static final Map<Long, UsuarioDocument> USUARIO_COLLECTION = new HashMap<Long, UsuarioDocument>();

	private static final Map<Long, TagDocument> TAG_COLLECTION = new HashMap<Long, TagDocument>();

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Map<Long, UsuarioDocument> getUsuarioCollection() {
		return USUARIO_COLLECTION;
	}

	public Map<Long, TagDocument> getTagDocument() {
		return TAG_COLLECTION;
	}

}
