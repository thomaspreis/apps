package net.thomaspreis.app.dfc.model;

import java.io.Serializable;

public class ExcelSheetConfigModel implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -9209737690112640819L;

	private Integer firstValidLineNumber;
	private Integer columnSourceName;
	private Integer columnTargetName;
	private Integer columnVersion;

	public ExcelSheetConfigModel() {
		this.firstValidLineNumber = Integer.valueOf(0);
		this.columnSourceName = Integer.valueOf(0);
		this.columnTargetName = Integer.valueOf(0);
		this.columnVersion = Integer.valueOf(0);
	}

	public Integer getFirstValidLineNumber() {
		return firstValidLineNumber;
	}

	public void setFirstValidLineNumber(Integer firstValidLineNumber) {
		this.firstValidLineNumber = firstValidLineNumber;
	}

	public Integer getColumnSourceName() {
		return columnSourceName;
	}

	public void setColumnSourceName(Integer columnSourceName) {
		this.columnSourceName = columnSourceName;
	}

	public Integer getColumnTargetName() {
		return columnTargetName;
	}

	public void setColumnTargetName(Integer columnTargetName) {
		this.columnTargetName = columnTargetName;
	}

	public Integer getColumnVersion() {
		return columnVersion;
	}

	public void setColumnVersion(Integer columnVersion) {
		this.columnVersion = columnVersion;
	}

}
