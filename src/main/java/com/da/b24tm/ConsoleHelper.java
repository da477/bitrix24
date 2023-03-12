package com.da.b24tm;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.slf4j.LoggerFactory.getLogger;

public class ConsoleHelper {
	private static final Logger log = getLogger(Bitrix24.class);
	private static final BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

	public static void writeMessage(String message) {
		log.debug(message);
	}

	public static String readString() throws IOException {
		return bis.readLine();
	}

	public static int readInt() throws IOException {
		String text = readString();
		return Integer.parseInt(text.trim());
	}
}
