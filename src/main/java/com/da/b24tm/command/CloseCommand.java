package com.da.b24tm.command;

import com.da.b24tm.ConsoleHelper;
import com.da.b24tm.HttpCon;
import com.da.b24tm.Operation;

public class CloseCommand implements Command {

	@Override
	public String execute() throws Exception {

		ConsoleHelper.writeMessage("Send: " + Operation.CLOSED);

		HttpCon.getInstance().getHttpConnection("close");

		HttpCon.getInstance().analiseResponseCode();

		return "";

	}

}
