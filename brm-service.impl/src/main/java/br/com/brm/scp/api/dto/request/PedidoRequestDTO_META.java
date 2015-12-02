package br.com.brm.scp.api.dto.request;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.SkuDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class PedidoRequestDTO_META implements Serializable {
	
	private static final long serialVersionUID = -3771437361484537539L;
	
	static final Class<?> USUARIO = UsuarioDocument.class;
	static final Class<?> SKU = SkuDocument.class;
	
}
