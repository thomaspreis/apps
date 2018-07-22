package net.thomaspreis.app.dfc.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import net.thomaspreis.app.dfc.base.ManagerBase;
import net.thomaspreis.app.dfc.model.ExcelEntryModel;
import net.thomaspreis.app.dfc.model.ExcelSheetConfigModel;

public class ExcelManager extends ManagerBase {

	private ExcelSheetConfigModel excelCfg;
	private String inputFileName;

	public ExcelManager(ExcelSheetConfigModel excelCfg, String inputFileName) {
		this.excelCfg = excelCfg;
		this.inputFileName = inputFileName;

	}

	public List<ExcelEntryModel> readContent() throws IOException, InvalidFormatException {
		final List<ExcelEntryModel> list = new ArrayList<>();
		super.debug("Reading content - excel file: " + inputFileName);

		Workbook workbook = WorkbookFactory.create(new File(inputFileName));
		Sheet sheet = workbook.getSheetAt(0);
		DataFormatter dataFormatter = new DataFormatter();

		sheet.forEach(row -> {
			if (this.excelCfg.getFirstValidLineNumber().intValue() <= row.getRowNum()) {
				ExcelEntryModel entry = new ExcelEntryModel();
				entry.setRowNumber(row.getRowNum());
				row.forEach(cell -> {
					String cellValue = dataFormatter.formatCellValue(cell);
					if (this.excelCfg.getColumnSourceName().intValue() == cell.getColumnIndex()) {
						entry.setSourceName(cellValue);
					} else if (this.excelCfg.getColumnTargetName().intValue() == cell.getColumnIndex()) {
						entry.setTargetName(cellValue);
					} else if (this.excelCfg.getColumnVersion().intValue() == cell.getColumnIndex()) {
						entry.setVersionNumber(cellValue);
					}
				});
				super.debug("Adding excel entry: " + entry.toString());
				list.add(entry);
			}
		});
		workbook.close();
		super.debug(String.format("Finished reading excel content, file: %s, entries found: %s", inputFileName,
				list.size()));
		return list;
	}
}
