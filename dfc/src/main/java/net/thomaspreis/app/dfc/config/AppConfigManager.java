package net.thomaspreis.app.dfc.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import net.thomaspreis.app.dfc.domain.AppConfigEnum;
import net.thomaspreis.app.dfc.model.ExcelSheetConfigModel;
import net.thomaspreis.app.dfc.model.MainGridModel;

public class AppConfigManager {

	private static Logger LOGGER = Logger.getLogger(AppConfigManager.class);

	private static String configPropsFileName = "dfr.properties";

	private static String currentUsersHomeDir = System.getProperty("user.home");

	private Properties properties = new Properties();
	private Gson gson = new Gson();

	public AppConfigManager() {
		init();
	}

	private void init() {
		if (!existsProperites()) {
			writeEmptyProperties();
		}
		loadProperties();
	}

	private void loadProperties() {
		InputStream input = null;
		try {
			input = new FileInputStream(getPropertiesFile());
			// load a properties file
			properties.load(input);
			LOGGER.info(properties.getProperty(AppConfigEnum.MAIN_GRID_MODEL.getKey()));
		} catch (IOException ex) {
			LOGGER.error(ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LOGGER.error(e);
				}
			}
		}
	}

	public final void writeProperties() {
		OutputStream output = null;
		try {
			output = new FileOutputStream(getPropertiesFile());
			this.properties.store(output, null);
		} catch (IOException io) {
			LOGGER.error(io);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					LOGGER.error(e);
				}
			}
		}
	}

	private void writeEmptyProperties() {
		OutputStream output = null;
		try {
			output = new FileOutputStream(getPropertiesFile());
			this.properties.setProperty(AppConfigEnum.MAIN_GRID_MODEL.getKey(), toJson(getNewMainGridModel()));
			this.properties.setProperty(AppConfigEnum.EXCEL_SHEET_CONFIG_MODEL.getKey(),
					toJson(getNewExcelSheetConfigModel()));
			this.properties.store(output, null);
		} catch (IOException io) {
			LOGGER.error(io);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					LOGGER.error(e);
				}
			}
		}
	}

	private ExcelSheetConfigModel getNewExcelSheetConfigModel() {
		ExcelSheetConfigModel m = new ExcelSheetConfigModel();
		m.setColumnSourceName(Integer.valueOf(3));
		m.setColumnTargetName(Integer.valueOf(0));
		m.setColumnVersion(Integer.valueOf(1));
		m.setFirstValidLineNumber(Integer.valueOf(7));
		return m;
	}

	private MainGridModel getNewMainGridModel() {
		MainGridModel m = new MainGridModel();
		m.setFileExtension(".DWG");
		m.setSourceFolder("/home/source/folder/");
		m.setSourceExcelFolder("/home/source/folder/xlsx/");
		m.setTargetFolder("/home/target/folder/");
		return m;
	}

	public final MainGridModel getMainGridModel() {
		return gson.fromJson(properties.getProperty(AppConfigEnum.MAIN_GRID_MODEL.getKey()), MainGridModel.class);
	}

	public final ExcelSheetConfigModel getExcelSheetConfigModel() {
		return gson.fromJson(properties.getProperty(AppConfigEnum.EXCEL_SHEET_CONFIG_MODEL.getKey()),
				ExcelSheetConfigModel.class);
	}

	public void setPropery(MainGridModel model) {
		this.properties.setProperty(AppConfigEnum.MAIN_GRID_MODEL.getKey(), toJson(model));
	}

	public void setPropery(ExcelSheetConfigModel model) {
		this.properties.setProperty(AppConfigEnum.EXCEL_SHEET_CONFIG_MODEL.getKey(), toJson(model));
	}

	private String toJson(Object object) {
		return gson.toJson(object);
	}

	private boolean existsProperites() {
		return new File(this.getPropertiesFile()).exists();
	}

	private String getPropertiesFile() {
		return currentUsersHomeDir + File.separatorChar + configPropsFileName;
	}
}
