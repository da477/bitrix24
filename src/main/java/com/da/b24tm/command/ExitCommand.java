package com.da.b24tm.command;

import com.da.b24tm.ConsoleHelper;

public class ExitCommand implements Command {
	@Override
	public String execute() {
		ConsoleHelper.writeMessage("Bye!");
		return "";
	}
}
