package model.enums;

public enum AccessTypes {
	CONTRIBUTOR("ADMIN"),
	ADMIN("ADMIN"),
	MASTER("MASTER");
	
	private String text;
	
	AccessTypes(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
