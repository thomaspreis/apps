package net.thomaspreis.app.dfc.config;

import junit.framework.TestCase;
import net.thomaspreis.app.dfc.config.AppConfigManager;
import net.thomaspreis.app.dfc.model.MainGridModel;

public class AppConfigManagerTest extends TestCase {

	public void testAppConfigManager() {
		AppConfigManager appCfg = new AppConfigManager();
		assertTrue(appCfg.getMainGridModel() != null);

		MainGridModel model = appCfg.getMainGridModel();
		model.setSourceExcelFolder("c:/source-xls-files");
		model.setFileExtension(".DWG");
		model.setSourceFolder("c:/source-folder");
		model.setTargetFolder("c:/target-folder");
		appCfg.setPropery(model);
		appCfg.writeProperties();
	}
}
