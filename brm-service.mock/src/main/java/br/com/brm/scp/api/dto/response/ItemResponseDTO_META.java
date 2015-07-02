package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

import br.com.brm.scp.mock.api.service.document.CategoriaDocument;
import br.com.brm.scp.mock.api.service.document.FornecedorDocument;

public class ItemResponseDTO_META implements Serializable {
	
	private static final long serialVersionUID = 810462106584376599L;
	
	static final Class<?> CATEGORIA = CategoriaDocument.class;
	static final Class<?> FORNECEDOR = FornecedorDocument.class;

}
