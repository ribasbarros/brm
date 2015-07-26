package br.com.brm.scp.mock.api.mockdata;

import java.util.HashMap;
import java.util.Map;

import br.com.brm.scp.mock.api.service.document.CategoriaDocument;
import br.com.brm.scp.mock.api.service.document.FornecedorDocument;
import br.com.brm.scp.mock.api.service.document.ItemDocument;
import br.com.brm.scp.mock.api.service.document.PedidoDocument;
import br.com.brm.scp.mock.api.service.document.SkuDocument;
import br.com.brm.scp.mock.api.service.document.TagDocument;
import br.com.brm.scp.mock.api.service.document.UsuarioDocument;

public class MockData {

	private static final Map<Long, UsuarioDocument> USUARIO_COLLECTION = new HashMap<Long, UsuarioDocument>();

	private static final Map<Long, TagDocument> TAG_COLLECTION = new HashMap<Long, TagDocument>();

	private static final Map<Long, SkuDocument> SKU_COLLECTION = new HashMap<Long, SkuDocument>();

	private static final Map<Long, FornecedorDocument> FORNECEDOR_COLLECTION = new HashMap<Long, FornecedorDocument>();

	private static final Map<Long, ItemDocument> ITEM_COLLECTION = new HashMap<Long, ItemDocument>();

	private static final Map<Long, CategoriaDocument> CATEGORIA_COLLECTION = new HashMap<Long, CategoriaDocument>();
	
	private static final Map<Long, PedidoDocument> PEDIDO_COLLECTION = new HashMap<Long, PedidoDocument>();
	
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
	
	public Map<Long, CategoriaDocument> getCategoriaCollection() {
		return CATEGORIA_COLLECTION;
	}
	
	public Map<Long, PedidoDocument> getPedidoCollection() {
		return PEDIDO_COLLECTION;
	}
}
