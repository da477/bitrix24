package com.da.b24tm;

import com.da.b24tm.command.*;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

	private static final Map<Operation, Command> allKnownCommandsMap = new HashMap<>();

	static {
		allKnownCommandsMap.put(Operation.OPENED, new OpenCommand());
		allKnownCommandsMap.put(Operation.PAUSED, new PauseCommand());
		allKnownCommandsMap.put(Operation.CLOSED, new CloseCommand());
		allKnownCommandsMap.put(Operation.STATUS, new StatusCommand());
		allKnownCommandsMap.put(Operation.AUTO, new AutoCommand());
		allKnownCommandsMap.put(Operation.EXIT, new ExitCommand());
	}

	private CommandExecutor() {
	}

	public static String execute(Operation operation) throws Exception {
		return allKnownCommandsMap.get(operation).execute();
	}

	public static Command getCommand(Operation operation) {
		return allKnownCommandsMap.get(operation);
	}

}
