package model.exceptions;

public class DBException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String message; 
	
	public DBException(String message) {
		this.message = message;
	}
	
	public DBException() {
		this.message = "An db operation exception has occurred";
	}
}
