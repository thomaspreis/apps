package net.thomaspreis.app.dfc.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import net.thomaspreis.app.dfc.base.ManagerBase;

public class FileManager extends ManagerBase {

	private static final String[] FILES_EXTENSIONS = new String[] { "xls", "xlsx" };

	private String sourceFolder;
	private String targetFolder;
	private String excelFileName;
	private List<String> reportEntries;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd.HHmm");

	public FileManager(String sourceFolder, String targetFolder) {
		this.sourceFolder = sourceFolder;
		this.targetFolder = targetFolder;
		init();
	}

	private void init() {
		this.createFolders(this.sourceFolder);
		this.createFolders(this.targetFolder);
	}

	public void initReport(String excelFileName) {
		this.reportEntries = new ArrayList<String>();
		this.excelFileName = excelFileName;
	}

	public void finishReport() {
		if (!this.reportEntries.isEmpty()) {
			String fileName = getReportFileName();
			try {
				BufferedWriter writer;
				writer = new BufferedWriter(new FileWriter(fileName, true));
				for (String msg : this.reportEntries) {
					writer.append(msg);
					writer.append("\n");
				}
				writer.close();
			} catch (IOException e) {
				super.error("Error writing report: " + fileName, e);
			}
		} else {
			super.debug("No report necessary for: " + this.excelFileName);
		}
	}

	private String getReportFileName() {
		File excelFile = new File(this.excelFileName);
		String reportFileName = excelFile.getName().substring(0, excelFile.getName().lastIndexOf("."));
		return String.format("%sReport-%s.%s.txt", this.sourceFolder, reportFileName, sdf.format(new Date()));
	}

	private void createFolders(String folderName) {
		File fileFolder = new File(folderName);
		if (!fileFolder.exists()) {
			super.debug("Folder does not exists: " + folderName);
			fileFolder.mkdirs();
			super.debug("Folder created: " + folderName);
		} else {
			super.debug("Using folder: " + folderName);
		}
	}

	public List<File> listExcelFiles(String xlsPath) {
		File dir = new File(xlsPath);
		return (List<File>) FileUtils.listFiles(dir, FILES_EXTENSIONS, true);
	}

	public void moveFile(String source, String target, String fileExtension) {
		String sourceName = getFileName(sourceFolder + File.separatorChar + source + fileExtension);
		String targetName = getFileName(targetFolder + File.separatorChar + target + fileExtension);
		super.debug(String.format("Processing file %s target %s", sourceName, targetName));
		File sourceFile = new File(sourceName);
		if (!sourceFile.exists()) {
			super.debug(String.format("The source file %s does not exists, skiping this file", sourceName));
		} else {
			File targetFile = new File(targetName);
			if (targetFile.exists()) {
				String msg = String
						.format(String.format("The target file %s already exists, skiping the file move", targetName));
				addReportEntry(msg);
			} else {
				try {
					FileUtils.moveFile(sourceFile, targetFile);
					String msg = String.format("File %s was moved to %s", sourceName, targetName);
					addReportEntry(msg);
				} catch (IOException e) {
					super.error(String.format("Error moving the file %s to %s", sourceName, targetName), e);
				}
			}
		}
	}

	private void addReportEntry(String msg) {
		super.debug(msg);
		this.reportEntries.add(msg);
	}

	private String getFileName(String fileName) {
		try {
			return fileName.replaceAll(String.valueOf(File.separatorChar) + String.valueOf(File.separatorChar),
					String.valueOf(File.separatorChar));
		} catch (Exception e) {
		}
		return fileName;
	}
}
