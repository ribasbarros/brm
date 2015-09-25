package br.com.brm.scp.api.exceptions;

public class TagExistenteException extends TagException{

	private static final long serialVersionUID = -2407105126060672599L;
		
	public TagExistenteException() {
		super();
	}

	public TagExistenteException(String message) {
		super(message);
	}
	
}
