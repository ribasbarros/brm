package br.com.brm.scp.api.dto.response;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.GrupoDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class UsuarioResponseDTO_META implements Serializable{

	private static final long serialVersionUID = -3120957225600946966L;
	static final Class<?> GRUPOS = GrupoDocument.class;
	static final Class<?> USUARIO = UsuarioDocument.class;

}
