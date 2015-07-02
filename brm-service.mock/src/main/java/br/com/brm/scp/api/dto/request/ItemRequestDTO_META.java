package br.com.brm.scp.api.dto.request;

import java.io.Serializable;

import br.com.brm.scp.mock.api.service.document.CategoriaDocument;
import br.com.brm.scp.mock.api.service.document.FornecedorDocument;

public class ItemRequestDTO_META implements Serializable {

	private static final long serialVersionUID = 8208607082984139165L;
	
	static final Class<?> CATEGORIA = CategoriaDocument.class;
	static final Class<?> FORNECEDOR = FornecedorDocument.class;
	
}
