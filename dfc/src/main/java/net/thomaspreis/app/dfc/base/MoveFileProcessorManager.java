package net.thomaspreis.app.dfc.base;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.thomaspreis.app.dfc.excel.ExcelManager;
import net.thomaspreis.app.dfc.file.FileManager;
import net.thomaspreis.app.dfc.model.ExcelEntryModel;
import net.thomaspreis.app.dfc.model.ExcelSheetConfigModel;

public class MoveFileProcessorManager extends ManagerBase {

	private String sourceExcelFolder;
	private String fileExtension;
	private String sourceFolder;
	private String targetFolder;
	private ExcelSheetConfigModel excelCfg;

	private ExcelManager excelManager;
	private FileManager fileManager;

	public MoveFileProcessorManager(String sourceExcelFolder, String fileExtension, String sourceFolder,
			String targetFolder, ExcelSheetConfigModel excelCfg) {
		this.sourceExcelFolder = sourceExcelFolder;
		this.fileExtension = fileExtension;
		this.sourceFolder = sourceFolder;
		this.targetFolder = targetFolder;
		this.excelCfg = excelCfg;

	}

	public Boolean process() throws InvalidFormatException, IOException {
		this.fileManager = new FileManager(this.sourceFolder, this.targetFolder);
		for (File excelFile : this.fileManager.listExcelFiles(this.sourceExcelFolder)) {
			this.fileManager.initReport(excelFile.getAbsolutePath());
			this.excelManager = new ExcelManager(this.excelCfg, excelFile.getAbsolutePath());
			final List<ExcelEntryModel> entriesList = this.excelManager.readContent();
			super.debug("Starting to moving and rename files");
			for (ExcelEntryModel eem : entriesList) {
				String sourceFileName = cleanFileName(eem.getSourceName());
				super.debug(
						String.format("Searching file to move %s instead of %s", sourceFileName, eem.getSourceName()));
				this.fileManager.moveFile(sourceFileName, eem.getTargetName(), this.fileExtension);
			}
			this.fileManager.finishReport();
			super.debug("Finished - moving and rename files");
		}
		return Boolean.TRUE;
	}

	private String cleanFileName(String fileName) {
		String target = fileName.replaceAll("\"", "''");
		target = target.replaceAll("/", "_");
		return target;
	}

}
