package com.da.b24tm.command;

import com.da.b24tm.ConsoleHelper;
import com.da.b24tm.HttpCon;

import static com.da.b24tm.Operation.OPENED;

public class OpenCommand implements Command {
	@Override
	public String execute() throws Exception {

		ConsoleHelper.writeMessage("Send: " + OPENED);

		HttpCon.getInstance().getHttpConnection(OPENED.getValue());
		HttpCon.getInstance().analiseResponseCode();

		return "";

	}

}
