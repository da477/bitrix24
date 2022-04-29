package bitrix24.command;

import bitrix24.ConsoleHelper;
import bitrix24.HttpCon;
import bitrix24.Operation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class StatusCommand implements Command {

	private Operation currentOperation;

	@Override
	public void execute() throws Exception {

		ConsoleHelper.writeMessage("Send to the Bitrix24: " + Operation.STATUS);

		HttpCon.getHttpConnection("status");

		int rescode = HttpCon.httpCon.getResponseCode();

		if (rescode == 200) {

			try (BufferedReader reader
						 = new BufferedReader(
					new InputStreamReader(HttpCon.httpCon.getInputStream(), StandardCharsets.UTF_8))) {

				String line = reader.lines().collect(Collectors.joining(System.lineSeparator()));

				String status = HttpCon.parseJSON(line);
				currentOperation = Operation.valueOf(status.toUpperCase());

				ConsoleHelper.writeMessage("Current status: " + currentOperation);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			ConsoleHelper.writeMessage("Something was wrong. Response code = " + rescode);
		}

	}

	public Operation getCurrentOperation() {
		ConsoleHelper.writeMessage("StatusCommand.currentOperation: " + currentOperation);
		return currentOperation;
	}

}
