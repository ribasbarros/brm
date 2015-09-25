package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.PerfilDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class GrupoResponseDTO_META implements Serializable{

	private static final long serialVersionUID = -4972910259797594230L;	
	static final Class<?> PERFIS = PerfilDocument.class;
	static final Class<?> USUARIO = UsuarioDocument.class;

}
