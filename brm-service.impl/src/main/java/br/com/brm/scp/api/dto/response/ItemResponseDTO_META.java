package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.CategoriaDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class ItemResponseDTO_META implements Serializable {
	
	private static final long serialVersionUID = 2378639082847408705L;
	
	static final Class<?> CATEGORIA = CategoriaDocument.class;
	static final Class<?> USUARIO = UsuarioDocument.class;
	
}
