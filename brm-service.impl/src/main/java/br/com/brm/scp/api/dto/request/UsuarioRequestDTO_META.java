package br.com.brm.scp.api.dto.request;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.GrupoDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class UsuarioRequestDTO_META implements Serializable{

	private static final long serialVersionUID = -646491334029356527L;
	static final Class<?> GRUPOS = GrupoDocument.class;
	static final Class<?> USUARIO = UsuarioDocument.class;

}
