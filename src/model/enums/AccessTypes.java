package model.enums;

public enum AccessTypes {
	CONTRIBUTOR("CONTRIBUTOR"),
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
