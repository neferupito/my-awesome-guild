package io.neferupito.myawesomeguild.core.blizzard.client;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

@Getter
public abstract class BlizzardClient {

    private String scheme = "https";
    private String authority = ".api.blizzard.com";
    private String tokenQuery = "access_token=US2TXZP5lGFh1tQuIUTIdQUlT94avVRqyv";
    private String localeQuery = "locale=";


//    https://eu.api.blizzard.com/wow/data/battlegroups/?locale=fr_FR&access_token=US2TXZP5lGFh1tQuIUTIdQUlT94avVRqyv

    protected String invokeBlizzardApi(URI uri) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(uri.toASCIIString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}
