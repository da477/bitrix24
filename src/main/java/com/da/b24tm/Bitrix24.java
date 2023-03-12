/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.da.b24tm;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bitrix24 {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");

	public static Operation askOperation() throws IOException {
		ConsoleHelper.writeMessage("");
		ConsoleHelper.writeMessage("Select operation:");
		ConsoleHelper.writeMessage(String.format("\t %d - open day", Operation.OPENED.ordinal()));
		ConsoleHelper.writeMessage(String.format("\t %d - pause day", Operation.PAUSED.ordinal()));
		ConsoleHelper.writeMessage(String.format("\t %d - close day", Operation.CLOSED.ordinal()));
		ConsoleHelper.writeMessage(String.format("\t %d - current status", Operation.STATUS.ordinal()));
		ConsoleHelper.writeMessage(String.format("\t %d - auto operation", Operation.AUTO.ordinal()));
		ConsoleHelper.writeMessage(String.format("\t %d - exit programm", Operation.EXIT.ordinal()));

		return Operation.values()[ConsoleHelper.readInt()];
	}

	private static void runApp(String[] args) {

		if (args.length == 0) {
			ConsoleHelper.writeMessage("Start: " + dateFormat.format(new Date()));

			Operation operation = null;
			try {
				String result = CommandExecutor.execute(Operation.STATUS);

				do {
					try {
						operation = askOperation();
						result = CommandExecutor.execute(operation);
					} catch (Exception e) {
						ConsoleHelper.writeMessage("Error. Check your data." + e.getMessage());
					}
				} while (operation != Operation.EXIT);
			} catch (Exception ex) {
				Logger.getLogger(Bitrix24.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public static void main(String[] args) {
		runApp(args);
	}

}
