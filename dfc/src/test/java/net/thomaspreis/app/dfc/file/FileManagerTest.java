package net.thomaspreis.app.dfc.file;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;
import net.thomaspreis.app.dfc.file.FileManager;

public class FileManagerTest extends TestCase {
	private static final String SOURCE_PATH = "./src/test/resources/";
	private static final String TARGET_PATH = "./src/target/test/";
	private static final String SOURCE_FILE_NAME = "INPASA-LI-CAL-401-001=01.xlsx";
	private static final String TARGET_FILE_NAME = "RENAMED-INPASA-LI-CAL-401-001-01.xlsx";

	public void testMoveFile() {
		new File(TARGET_PATH + TARGET_FILE_NAME).delete();
		FileManager fileManager = new FileManager(SOURCE_PATH, TARGET_PATH);
		fileManager.moveFile(SOURCE_FILE_NAME, TARGET_FILE_NAME, ".xlsx");
		assertTrue(Boolean.TRUE);
	}

	public void testListExcelFiles() {
		FileManager fileManager = new FileManager(SOURCE_PATH, TARGET_PATH);
		List<File> files = fileManager.listExcelFiles(SOURCE_PATH);
		assertTrue(!files.isEmpty());
	}
}
