package com.starwars.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class SwapiRequestExecutor {

    private static final String URL = "http:";
    private static final String URL_REPLACE = "https:";

    public <T> T get(String url, Class<T> classReturn) {
        HttpURLConnection conn = null;
        try {
            String urlFinal = validUrl(url);

            conn = create(urlFinal);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("Erro " + conn.getResponseCode() + " ao obter dados da URL " + url);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output = "";
            String line;

            while ((line = br.readLine()) != null) {
                output += line;
            }

            conn.disconnect();

            return getResultResponse(classReturn, output);


        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    HttpURLConnection create(String url) throws IOException {
        return (HttpURLConnection) new URL(url).openConnection();
    }

    private String validUrl(String url) {
        if (url.contains(URL)) {
            return url.replace(URL, URL_REPLACE);
        }
        return url;
    }

    private <T> T getResultResponse(Class<T> classReturn, String output) {
        Gson gson = new Gson();
        return gson.fromJson(new String(output.getBytes()), classReturn);
    }

}
