package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.SkuDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class PedidoResponseDTO_META implements Serializable {

	private static final long serialVersionUID = 426015481124546592L;
	
	static final Class<?> USUARIO = UsuarioDocument.class;
	static final Class<?> SKU = SkuDocument.class;
	
}
