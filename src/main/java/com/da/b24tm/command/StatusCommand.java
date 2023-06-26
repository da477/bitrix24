package com.da.b24tm.command;

import com.da.b24tm.ConsoleHelper;
import com.da.b24tm.HttpCon;
import com.da.b24tm.Operation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class StatusCommand implements Command {

    private Operation currentOperation;

    @Override
    public String execute() throws Exception {

        String status = "";

        HttpCon.getInstance().getHttpConnection(Operation.STATUS.getValue());

        int responseCode = HttpCon.getInstance().getResponseCode();

        if (responseCode == 200) {

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(HttpCon.getInstance().getInputStream(), StandardCharsets.UTF_8))) {

                String line = reader.lines().collect(Collectors.joining(System.lineSeparator()));

                status = HttpCon.getInstance().parseJSON(line);
                currentOperation = Operation.valueOf(status.toUpperCase());

                ConsoleHelper.writeMessage("Current status: " + currentOperation);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            ConsoleHelper.writeMessage("Something was wrong. Response code: " + responseCode);
        }

        return status;

    }

    public Operation getCurrentOperation() {
        ConsoleHelper.writeMessage("Current Operation: " + currentOperation);
        return currentOperation;
    }

}
