package com.da.b24tm.command;

import com.da.b24tm.CommandExecutor;
import com.da.b24tm.ConsoleHelper;
import com.da.b24tm.NotWorkingDays;
import com.da.b24tm.Operation;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class AutoCommand implements Command {

	public static final NotWorkingDays newDays = new NotWorkingDays();
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");

	private static Operation defineCommand(Operation status) {

		LocalDateTime curDateTime = LocalDateTime.now();

		Operation cmnd            = null;
		int       hour            = curDateTime.getHour();
		int       DayofWeek       = curDateTime.getDayOfWeek().getValue();
		boolean   todayNotWorking = newDays.todayNotWorking(curDateTime);
		boolean   todayAddWorkDay = newDays.todayAddWorkDay(curDateTime);

		if (!todayAddWorkDay) {
			if (DayofWeek > 5 || todayNotWorking) {
				return cmnd;
			}
		}
		if (hour >= 18 && status == Operation.OPENED) {
			return Operation.CLOSED;
		}
		if (hour >= 9 && hour < 18 && (status == Operation.CLOSED || status == Operation.PAUSED)) {
			cmnd = Operation.OPENED;
		}
		return cmnd;
	}

	@Override
	public String execute() {

		ConsoleHelper.writeMessage("AutoStart: " + dateFormat.format(new Date()));

		try {
			CommandExecutor.execute(Operation.STATUS);
			StatusCommand statusCommand = (StatusCommand) CommandExecutor.getCommand(Operation.STATUS);

			Operation curentStatus = statusCommand.getCurrentOperation();

			if (curentStatus != null) {

				Operation newCommand = defineCommand(curentStatus);

				if (newCommand != null) {
					CommandExecutor.execute(newCommand);
					CommandExecutor.execute(Operation.STATUS);
				} else {
					ConsoleHelper.writeMessage("Doesn't need to send a command at the moment. Exit.");
				}
				ConsoleHelper.writeMessage("Finish: " + dateFormat.format(new Date()));

			} else {
				throw new Exception("Error. Answer=false.");
			}

		} catch (Exception e) {
			ConsoleHelper.writeMessage("Error. There was no answer from the timeman:" + e);
		}

		return "";
	}

}
