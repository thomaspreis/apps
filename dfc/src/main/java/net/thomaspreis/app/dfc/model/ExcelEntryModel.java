package net.thomaspreis.app.dfc.model;

import java.io.Serializable;

public class ExcelEntryModel implements Serializable {

	/**
	 * ExcelEntryModel
	 */
	private static final long serialVersionUID = -2188898763713559538L;

	private Integer rowNumber;
	private String sourceName;
	private String targetName;
	private String versionNumber;

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	@Override
	public String toString() {
		return "ExcelEntryModel [rowNumber=" + rowNumber + ", sourceName=" + sourceName + ", targetName=" + targetName
				+ ", versionNumber=" + versionNumber + "]";
	}

}
