package br.com.brm.scp.api.exceptions;

public class FornecedorIsUsedException extends TagException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4121891421124783799L;

	public FornecedorIsUsedException() {
		super();
	}
	
	public FornecedorIsUsedException(String message) {
		super(message);
	}
}
