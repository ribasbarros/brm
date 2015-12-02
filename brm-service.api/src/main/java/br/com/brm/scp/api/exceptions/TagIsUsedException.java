package br.com.brm.scp.api.exceptions;

public class TagIsUsedException extends TagException{

	private static final long serialVersionUID = 4699138829611973328L;
	public TagIsUsedException() {
		super();
	}
	
	public TagIsUsedException(String message) {
		super(message);
	}
}
