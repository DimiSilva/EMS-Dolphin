package model.exceptions;

public class EncryptionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String message; 
	
	public EncryptionException(String message) {
		this.message = message;
	}
	
	public EncryptionException() {
		this.message = "An encryption exception has occurred";
	}
}
