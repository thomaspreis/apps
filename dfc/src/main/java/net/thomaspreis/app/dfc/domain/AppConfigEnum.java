package net.thomaspreis.app.dfc.domain;

import net.thomaspreis.app.dfc.model.ExcelSheetConfigModel;
import net.thomaspreis.app.dfc.model.MainGridModel;

public enum AppConfigEnum {
	MAIN_GRID_MODEL(MainGridModel.class.getName()), 
	EXCEL_SHEET_CONFIG_MODEL(ExcelSheetConfigModel.class.getName());

	private String key;

	private AppConfigEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
