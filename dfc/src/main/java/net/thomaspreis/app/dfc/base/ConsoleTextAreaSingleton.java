package net.thomaspreis.app.dfc.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

public class ConsoleTextAreaSingleton {

	private static final Logger LOGGER = Logger.getLogger(ConsoleTextAreaSingleton.class);

	private static ConsoleTextAreaSingleton instance;

	private BlockingQueue<String> messageQueue;
	private SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss] ");

	private ConsoleTextAreaSingleton() {
	}

	public static final ConsoleTextAreaSingleton getInstance() {
		if (instance == null) {
			instance = new ConsoleTextAreaSingleton();
		}
		return instance;
	}

	public void append(String msg) {
		if (null != this.messageQueue) {
			try {
				this.messageQueue.put(getDate() + msg);
			} catch (InterruptedException e) {
				LOGGER.error(e);
			}
		}
	}

	private String getDate() {
		return sdf.format(new Date());
	}

	public void setMessageQueue(final BlockingQueue<String> messageQueue) {
		this.messageQueue = messageQueue;
	}
}
