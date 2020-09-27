package model.enums;

public enum Months {
	JANEIRO("Janeiro"),
	FEVEREIRO("Fevereiro"),
	MARCO("Mar�o"),
	ABRIL("Abril"),
	MAIO("Maio"),
	JUNHO("Junho"),
	JULHO("Julho"),
	AGOSTO("Agosto"),
	SETEMBRO("Setembro"),
	OUTUBRO("Outubro"),
	NOVEMBRO("Novembro"),
	DEZEMBRO("Dezembro");
	
	private String text;
	
	Months(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
