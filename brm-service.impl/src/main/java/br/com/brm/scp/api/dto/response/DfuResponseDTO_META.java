package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.ContatoDocument;
import br.com.brm.scp.api.service.document.FornecedorCentroDocument;
import br.com.brm.scp.api.service.document.ItemDocument;
import br.com.brm.scp.api.service.document.MatrizSkuDocument;
import br.com.brm.scp.api.service.document.TagDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class DfuResponseDTO_META implements Serializable{

	private static final long serialVersionUID = -6293717818527196388L;
	static final Class<?> ITEM = ItemDocument.class;
	static final Class<?> MATRIZES = MatrizSkuDocument.class;
	static final Class<?> TAGS = TagDocument.class;
	static final Class<?> USUARIO = UsuarioDocument.class;
	
}
