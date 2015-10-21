package br.com.brm.scp.api.dto.request;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.ItemDocument;
import br.com.brm.scp.api.service.document.RelacaoSkuDocument;
import br.com.brm.scp.api.service.document.TagDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class DfuRequestDTO_META implements Serializable{
	private static final long serialVersionUID = -6293717818527196388L;
	static final Class<?> ITEM = ItemDocument.class;
	static final Class<?> RELACAO = RelacaoSkuDocument.class;
	static final Class<?> TAGS = TagDocument.class;
	static final Class<?> USUARIO = UsuarioDocument.class;
}
