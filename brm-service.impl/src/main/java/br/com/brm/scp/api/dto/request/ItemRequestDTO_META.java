package br.com.brm.scp.api.dto.request;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.CategoriaDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class ItemRequestDTO_META implements Serializable {

	private static final long serialVersionUID = 5045509842729920660L;
	
	static final Class<?> CATEGORIA = CategoriaDocument.class;
	static final Class<?> USUARIO = UsuarioDocument.class;

}
