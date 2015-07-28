package br.com.brm.scp.api.exceptions;

public class GrupoExistenteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7095848594998778246L;

	public GrupoExistenteException(String string) {
		super(string);
	}
	
	public GrupoExistenteException() {
		super();
	}
}
