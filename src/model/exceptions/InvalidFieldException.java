package model.exceptions;

public class InvalidFieldException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String message; 
	
	public InvalidFieldException(String message) {
		this.message = message;
	}
	
	public InvalidFieldException() {
		this.message = "Erro ao atualizar o usuário";
	}
}
