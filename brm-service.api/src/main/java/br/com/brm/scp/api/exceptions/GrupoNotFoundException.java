package br.com.brm.scp.api.exceptions;

public class GrupoNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -177468478144797774L;

	public GrupoNotFoundException(String string) {
		super(string);
	}
	
	public GrupoNotFoundException() {
		super();
	}
}
