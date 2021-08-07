/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitrix24;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author da
 */
public class HttpCon {

    private String USER_AGENT, URL, WEBHOOK;
    public static HttpURLConnection httpCon;

    public HttpCon() {

        if (USER_AGENT == null) {
            try {
                Properties properties = new Properties();
                String pathConfig = "config.properties";
                InputStream inStream = getClass().getResource(pathConfig).openStream();

                properties.load(inStream);
                USER_AGENT = properties.getProperty("USER_AGENT");
                URL = properties.getProperty("URL");
                WEBHOOK = properties.getProperty("WEBHOOK");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void getHttpConnection(String method) throws Exception {

        try {

            StringBuilder stringBuilder = new StringBuilder(URL);
            stringBuilder.append(WEBHOOK);
            stringBuilder.append("timeman." + method);
            httpCon = (HttpURLConnection) new URL(stringBuilder.toString()).openConnection();
            httpCon.setRequestMethod("GET");
            httpCon.setRequestProperty("User-Agent", USER_AGENT);
            httpCon.setRequestProperty("Accept-Charset", "UTF-8");
        } catch (Exception e) {
            System.out.println("There is no answer from the timeman:" + method + "_" + e);
        }
    }

}
