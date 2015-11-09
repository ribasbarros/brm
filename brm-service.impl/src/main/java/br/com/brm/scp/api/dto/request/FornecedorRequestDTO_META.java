package br.com.brm.scp.api.dto.request;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.ContatoDocument;
import br.com.brm.scp.api.service.document.FornecedorCentroDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class FornecedorRequestDTO_META implements Serializable {

	private static final long serialVersionUID = -6650541874633353571L;
	
	static final Class<?> CONTATO = ContatoDocument.class;
	static final Class<?> CENTRO = FornecedorCentroDocument.class;
	static final Class<?> USUARIO = UsuarioDocument.class;

}
