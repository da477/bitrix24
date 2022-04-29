package bitrix24.command;

import bitrix24.ConsoleHelper;
import bitrix24.HttpCon;
import bitrix24.Operation;

public class OpenCommand implements Command {
	@Override
	public void execute() throws Exception {

		ConsoleHelper.writeMessage("Send: " + Operation.OPENED);

		HttpCon.getHttpConnection("open");
		HttpCon.analiseResponseCode();

	}

}
