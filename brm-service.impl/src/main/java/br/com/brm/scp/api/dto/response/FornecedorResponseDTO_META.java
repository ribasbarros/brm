package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.ContatoDocument;
import br.com.brm.scp.api.service.document.FornecedorCentroDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class FornecedorResponseDTO_META implements Serializable {

	private static final long serialVersionUID = -1060590317863159123L;
	
	static final Class<?> CONTATO = ContatoDocument.class;
	static final Class<?> CENTRO = FornecedorCentroDocument.class;
	static final Class<?> USUARIO = UsuarioDocument.class;

}
