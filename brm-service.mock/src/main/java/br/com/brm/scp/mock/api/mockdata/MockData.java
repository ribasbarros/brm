package br.com.brm.scp.mock.api.mockdata;

import java.util.HashMap;
import java.util.Map;

import br.com.brm.scp.mock.api.service.document.FornecedorDocument;
import br.com.brm.scp.mock.api.service.document.ItemDocument;
import br.com.brm.scp.mock.api.service.document.SkuDocument;
import br.com.brm.scp.mock.api.service.document.TagDocument;
import br.com.brm.scp.mock.api.service.document.UsuarioDocument;

public class MockData {

	private static final Map<Long, UsuarioDocument> USUARIO_COLLECTION = new HashMap<Long, UsuarioDocument>();

	private static final Map<Long, TagDocument> TAG_COLLECTION = new HashMap<Long, TagDocument>();

	private static final Map<Long, SkuDocument> SKU_COLLECTION = new HashMap<Long, SkuDocument>();

	private static final Map<Long, FornecedorDocument> FORNECEDOR_COLLECTION = new HashMap<Long, FornecedorDocument>();

	private static final Map<Long, ItemDocument> ITEM_COLLECTION = new HashMap<Long, ItemDocument>();

	public Map<Long, UsuarioDocument> getUsuarioCollection() {
		return USUARIO_COLLECTION;
	}

	public Map<Long, TagDocument> getTagCollection() {
		return TAG_COLLECTION;
	}

	public Map<Long, SkuDocument> getSkuCollection() {
		return SKU_COLLECTION;
	}

	public Map<Long, FornecedorDocument> getFornecedorCollection() {
		return FORNECEDOR_COLLECTION;
	}

	public Map<Long, ItemDocument> getItemCollection() {
		return ITEM_COLLECTION;
	}
}
