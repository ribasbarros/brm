package br.com.brm.scp.api.exceptions;

public class TagNotFoundException extends TagException{

	private static final long serialVersionUID = 4699138829611973328L;
	public TagNotFoundException() {
		super();
	}
	
	public TagNotFoundException(String message) {
		super(message);
	}
}
