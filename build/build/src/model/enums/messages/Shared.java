package model.enums.messages;

public enum Shared {
	SOMETHING_WENT_WRONG("Lamentamos, ocorreu um erro inesperado!");
	
	private String text;
	
	Shared(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
