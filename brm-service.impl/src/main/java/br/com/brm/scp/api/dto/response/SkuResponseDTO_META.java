package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.ItemDocument;
import br.com.brm.scp.api.service.document.OrigemSkuDocument;
import br.com.brm.scp.api.service.document.TagDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class SkuResponseDTO_META implements Serializable {
	
	private static final long serialVersionUID = 2378639082847408705L;
	
	static final Class<?> ITEM = ItemDocument.class;
	static final Class<?> TAGS = TagDocument.class;
	static final Class<?> ORIGEM = OrigemSkuDocument.class;
	static final Class<?> USUARIO = UsuarioDocument.class;

}
