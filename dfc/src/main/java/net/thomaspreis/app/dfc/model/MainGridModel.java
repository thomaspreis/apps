package net.thomaspreis.app.dfc.model;

import java.io.Serializable;

public class MainGridModel implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2128955624486770927L;

	private String sourceExcelFolder;
	private String sourceFolder;
	private String targetFolder;
	private String fileExtension;
	
	public MainGridModel() {
		this.sourceExcelFolder = "";
		this.sourceFolder = "";
		this.targetFolder = "";
		this.fileExtension = "";
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public String getTargetFolder() {
		return targetFolder;
	}

	public void setTargetFolder(String targetFolder) {
		this.targetFolder = targetFolder;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getSourceExcelFolder() {
		return sourceExcelFolder;
	}

	public void setSourceExcelFolder(String sourceExcelFolder) {
		this.sourceExcelFolder = sourceExcelFolder;
	}
	
	@Override
	public String toString() {
		return "MainGridModel [sourceExcelFolder=" + sourceExcelFolder + ", sourceFolder=" + sourceFolder + ", targetFolder="
				+ targetFolder + ", fileExtension=" + fileExtension + "]";
	}
}
