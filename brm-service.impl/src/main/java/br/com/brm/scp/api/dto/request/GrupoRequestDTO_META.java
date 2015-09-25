package br.com.brm.scp.api.dto.request;

import java.io.Serializable;

import br.com.brm.scp.api.service.document.PerfilDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;

public class GrupoRequestDTO_META implements Serializable{

	private static final long serialVersionUID = 1621324906521923045L;
	static final Class<?> PERFIS = PerfilDocument.class;
	static final Class<?> USUARIO = UsuarioDocument.class;

}
