package br.com.brm.scp.api.exceptions;

public class CategoriaIsUsedException extends TagException{

	private static final long serialVersionUID = -112360064935563108L;

	public CategoriaIsUsedException() {
		super();
	}
	
	public CategoriaIsUsedException(String message) {
		super(message);
	}
}
