package net.thomaspreis.app.dfc.domain;

public enum AppResourcesEnum {
	PROCESS_BUTTON("Toca Ficha!"),
	EXCEL_FOLDER_LABEL("Diretório Arquivos Excel"),
	FILE_TYPE_LABEL("Tipos de arquivo para cópia"),
	SOURCE_FOLDER_LABEL("Diretório origem"),
	TARGET_FOLDER_LABEL("Diretório destino");
	private String value;

	private AppResourcesEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
