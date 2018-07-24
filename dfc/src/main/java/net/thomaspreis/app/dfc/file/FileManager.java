package net.thomaspreis.app.dfc.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import net.thomaspreis.app.dfc.base.ManagerBase;

public class FileManager extends ManagerBase {

	private static final String[] FILES_EXTENSIONS = new String[] { "xls", "xlsx" };

	private String sourceFolder;
	private String targetFolder;

	public FileManager(String sourceFolder, String targetFolder) {
		this.sourceFolder = sourceFolder;
		this.targetFolder = targetFolder;
		init();
	}

	private void init() {
		this.createFolders(this.sourceFolder);
		this.createFolders(this.targetFolder);
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

	public void copyFile(String source, String target, String fileExtension) {
		String sourceName = getFileName(sourceFolder + File.separatorChar + source + fileExtension);
		String targetName = getFileName(targetFolder + File.separatorChar + target + fileExtension);
		super.debug(String.format("Processing file %s target %s", sourceName, targetName));
		File sourceFile = new File(sourceName);
		if (!sourceFile.exists()) {
			super.debug(String.format("The source file %s does not exists, skiping this file", sourceName));
		} else {
			File targetFile = new File(targetName);
			if (targetFile.exists()) {
				super.debug(String.format("The target file %s already exists, skiping the copy", targetName));
			} else {
				try {
					FileUtils.copyFile(sourceFile, targetFile);
					super.debug(String.format("File %s was copied to %s", sourceName, targetName));
				} catch (IOException e) {
					super.error(String.format("Error copying the file %s to %s", sourceName, targetName), e);
				}
			}
		}
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
