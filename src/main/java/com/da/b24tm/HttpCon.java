/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.da.b24tm;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author da
 */
public abstract class HttpCon {

    public static HttpURLConnection httpCon;
    private static Map<String, String> runProperties = new HashMap<>();

    static {
        Properties properties = new Properties();
        String pathConfig = "/config.properties";
//		try (InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathConfig)) {
        try (InputStream inStream = HttpCon.class.getResource(pathConfig).openStream()) {
            properties.load(inStream);
            runProperties = (Map) properties;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void getHttpConnection(String method) {

        try {
            StringBuilder sb = new StringBuilder(runProperties.get("URL"));
            sb.append(runProperties.get("WEBHOOK"));
            sb.append("timeman.").append(method);

            httpCon = (HttpURLConnection) new URL(sb.toString()).openConnection();
            httpCon.setRequestMethod("GET");
            httpCon.setRequestProperty("User-Agent", runProperties.get("USER_AGENT"));
            httpCon.setRequestProperty("Accept-Charset", "UTF-8");

        } catch (Exception e) {
            ConsoleHelper.writeMessage("There is no answer from the timeman: " + method + "_" + e);
        }

    }

    public static String parseJSON(String json) throws ParseException {
        JSONObject result = (JSONObject)
                ((JSONObject) new JSONParser().parse(json))
                        .get("result");
        return (String) result.get("STATUS");
    }

    public static void analiseResponseCode() throws IOException {

        int responseCode = httpCon.getResponseCode();

        if (responseCode == 200) {

            try (BufferedReader reader
                         = new BufferedReader(
                    new InputStreamReader(httpCon.getInputStream(), StandardCharsets.UTF_8))) {

                String line = reader.lines().collect(Collectors.joining(System.lineSeparator()));

                ConsoleHelper.writeMessage("Answer: " + line);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            ConsoleHelper.writeMessage("Something was wrong. Response code = " + responseCode);
        }
    }

}
