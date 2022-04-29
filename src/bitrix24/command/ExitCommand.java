package bitrix24.command;

import bitrix24.ConsoleHelper;

public class ExitCommand implements Command {
	@Override
	public void execute() throws Exception {
		ConsoleHelper.writeMessage("Bye!");
	}
}
