package com.da.b24tm;

import com.da.b24tm.command.*;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

	private static final Map<Operation, Command> commands = new HashMap<>();

	static {
		commands.put(Operation.OPENED, new OpenCommand());
		commands.put(Operation.PAUSED, new PauseCommand());
		commands.put(Operation.CLOSED, new CloseCommand());
		commands.put(Operation.STATUS, new StatusCommand());
		commands.put(Operation.AUTO, new AutoCommand());
		commands.put(Operation.EXIT, new ExitCommand());
	}

	private CommandExecutor() {
	}

	public static String execute(Operation operation) throws Exception {
		return getCommand(operation).execute();
	}

	public static Command getCommand(Operation operation) {
		return commands.get(operation);
	}

}
