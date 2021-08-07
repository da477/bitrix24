/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitrix24;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Bitrix24 {

    public static LocalDateTime curDateTime = LocalDateTime.now();
    public static NotWorkingDays newDays = new NotWorkingDays();
    public HttpCon httpobj = new HttpCon();

    public static void main(String[] args) throws Exception {

        System.out.println("Start: " + curDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        Bitrix24 http = new Bitrix24();

        try {
            Map<String, String> answer = http.sendGet("status");
            Boolean answerOk = answer.containsKey("JSON");

            if (answerOk) {

                String newCmnd = DefineCommand(curStatus(answer));
                if (!newCmnd.isEmpty()) {
                    http.sendGet(newCmnd);
                } else {
                    System.out.println("Doesn't need to send a command. Exit.");
                }

            } else {
                throw new Exception("Error. answerOk=" + answerOk.toString());
            }

        } catch (Exception e) {
            System.out.println("Error. There is no answer from the timeman:" + e);
        }

    }

    private static String curStatus(Map<String, String> answer) throws ParseException {
        JSONObject jobj = (JSONObject) new JSONParser().parse(answer.get("JSON"));
        JSONObject jresult = (JSONObject) jobj.get("result");
        String status = (String) jresult.get("STATUS");
        System.out.println("Current status: " + status);
        return status;
    }

    private static String DefineCommand(String status) {

        String cmnd = "";
        int hour = curDateTime.getHour();
        int DayofWeek = curDateTime.getDayOfWeek().getValue();

        Boolean todayNotWorking = newDays.todayNotWorking(curDateTime);
        Boolean todayAddWorkDay = newDays.todayAddWorkDay(curDateTime);

        if (!todayAddWorkDay) {
            if (DayofWeek > 5 || todayNotWorking) {
                return cmnd;
            }
        }

        if (hour >= 18 && status.equals("OPENED")) {
            return "close";
        }

        if (hour >= 9 && hour < 18 && (status.equals("CLOSED") || status.equals("PAUSED"))) {
            cmnd = "open";
        }

        return cmnd;
    }

    // HTTP GET request
    private Map sendGet(String method) throws Exception {

        System.out.println("Send a command: " + method);

        String answerStatus = "";
        Map<String, String> answerMap = new HashMap<>();

        httpobj.getHttpConnection(method);

        int rescode = httpobj.httpCon.getResponseCode();

        if (rescode == 200) {

            BufferedReader in = new BufferedReader(new InputStreamReader(httpobj.httpCon.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            answerMap.put("JSON", response.toString());
        } else {
            System.out.println("Something wrong, rescode=" + rescode);
        }

        return answerMap;

    }

}
