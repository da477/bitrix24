/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.da.b24tm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
public class HttpCon {

    private final ObjectMapper OM = new ObjectMapper();
    private HttpURLConnection httpCon;
    private Map<String, String> runProperties = new HashMap<>();

    private HttpCon() {
        Properties properties = new Properties();
        String pathConfig = "/config.properties"; //will look at the following location in your class path ./myfile.txt.
        try (InputStream inStream = HttpCon.class.getResourceAsStream(pathConfig)) {
            properties.load(inStream);
            this.runProperties = (Map) properties;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static HttpCon getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void getHttpConnection(String method) {

        try {
            String sb = this.runProperties.get("URL") + this.runProperties.get("WEBHOOK") +
                    "timeman." + method;

            URL url = new URL(sb);
            this.httpCon = (HttpURLConnection) url.openConnection();
            this.httpCon.setRequestMethod("GET");
            this.httpCon.setRequestProperty("User-Agent", runProperties.get("USER_AGENT"));
            this.httpCon.setRequestProperty("Accept-Charset", "UTF-8");

        } catch (Exception e) {
            ConsoleHelper.writeMessage("There is no answer from the timeman: " + method + "_" + e);
        }

    }

    public String parseJSON(String json) throws JsonProcessingException {
        JsonNode fieldStatus = this.OM.readTree(json).get("result").get("STATUS");
        return fieldStatus.asText();
    }

    public void analiseResponseCode() throws IOException {

        int responseCode = getResponseCode();

        if (responseCode == 200) {

            try (BufferedReader reader
                         = new BufferedReader(
                    new InputStreamReader(httpCon.getInputStream(), StandardCharsets.UTF_8))) {

                String line = reader
                        .lines()
                        .collect(Collectors.joining(System.lineSeparator()));

                ConsoleHelper.writeMessage("Answer: " + line);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            ConsoleHelper.writeMessage("Something was wrong. Response code = " + responseCode);
        }
    }

    public int getResponseCode() throws IOException {
        return this.httpCon.getResponseCode();
    }

    public InputStream getInputStream() throws IOException {
        return this.httpCon.getInputStream();
    }

    //    Initialization-on-demand holder idiom
//    https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class LazyHolder {
        static final HttpCon INSTANCE = new HttpCon();
    }

}
