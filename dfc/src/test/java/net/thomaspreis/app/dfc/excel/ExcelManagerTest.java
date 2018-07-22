package net.thomaspreis.app.dfc.excel;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import junit.framework.TestCase;
import net.thomaspreis.app.dfc.config.AppConfigManager;
import net.thomaspreis.app.dfc.excel.ExcelManager;
import net.thomaspreis.app.dfc.model.ExcelEntryModel;

public class ExcelManagerTest extends TestCase {

	private static final String SAMPLE_XLSX_FILE_PATH = "./src/test/resources/INPASA-LI-CAL-401-001=01.xlsx";

	private ExcelManager excelManager = null;

	public void testReadContent() throws InvalidFormatException, IOException {
		AppConfigManager appCfg = new AppConfigManager();
		this.excelManager = new ExcelManager(appCfg.getExcelSheetConfigModel(), SAMPLE_XLSX_FILE_PATH);
		List<ExcelEntryModel> list = this.excelManager.readContent();
		assertTrue(list.size() == 78);
	}
}
