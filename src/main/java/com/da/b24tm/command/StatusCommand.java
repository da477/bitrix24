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

        HttpCon.getHttpConnection("status");

        int responseCode = HttpCon.httpCon.getResponseCode();

        if (responseCode == 200) {

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(HttpCon.httpCon.getInputStream(), StandardCharsets.UTF_8))) {

                String line = reader.lines().collect(Collectors.joining(System.lineSeparator()));

                status = HttpCon.parseJSON(line);
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
