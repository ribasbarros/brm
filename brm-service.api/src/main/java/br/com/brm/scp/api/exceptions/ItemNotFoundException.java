package br.com.brm.scp.api.exceptions;

public class ItemNotFoundException extends ItemException {

	private static final long serialVersionUID = -1068474613578647257L;
	
	public ItemNotFoundException(String message) {
		super(message);
	}
	
}
