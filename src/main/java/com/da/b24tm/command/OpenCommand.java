package com.da.b24tm.command;

import com.da.b24tm.ConsoleHelper;
import com.da.b24tm.HttpCon;
import com.da.b24tm.Operation;

public class OpenCommand implements Command {
	@Override
	public String execute() throws Exception {

		ConsoleHelper.writeMessage("Send: " + Operation.OPENED);

		HttpCon.getHttpConnection(Operation.OPENED.getValue());
		HttpCon.analiseResponseCode();

		return "";

	}

}
