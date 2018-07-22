package net.thomaspreis.app.dfc.base;

import org.apache.log4j.Logger;

public abstract class ManagerBase {

	private static final Logger LOGGER = Logger.getLogger(ManagerBase.class);

	public void debug(String msg) {
		LOGGER.debug(msg);
		appendConsoleTextArea(msg);
	}

	public void error(String msg, Exception e) {
		LOGGER.error(msg, e);
		appendConsoleTextArea(msg);
	}

	private void appendConsoleTextArea(String msg) {
		ConsoleTextAreaSingleton.getInstance().append(msg);
	}
}
