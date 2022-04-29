package bitrix24.command;

import bitrix24.ConsoleHelper;
import bitrix24.HttpCon;
import bitrix24.Operation;

public class PauseCommand implements Command {
	@Override
	public void execute() throws Exception {
		ConsoleHelper.writeMessage("Send: " + Operation.PAUSED);

		HttpCon.getHttpConnection("pause");
		HttpCon.analiseResponseCode();
	}
}
