package br.com.brm.scp.api.dto.request;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.ItemDocument;
import br.com.brm.scp.api.service.document.OrigemSkuDocument;
import br.com.brm.scp.api.service.document.TagDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class SkuRequestDTO_META implements Serializable {

	private static final long serialVersionUID = -9092076356678280739L;
	
	static final Class<?> ITEM = ItemDocument.class;
	static final Class<?> TAGS = TagDocument.class;
	static final Class<?> ORIGEM = OrigemSkuDocument.class;
	static final Class<?> USUARIO = UsuarioDocument.class;


}
