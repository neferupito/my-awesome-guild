package io.neferupito.myawesomeguild.core.blizzard.client;

import com.google.gson.Gson;
import io.neferupito.myawesomeguild.core.blizzard.client.exception.BlizzardException;
import io.neferupito.myawesomeguild.core.blizzard.json.TokenBlz;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

@Getter
@Slf4j
public abstract class BlizzardClient {

    @Value("${blizzard.oauth.authorization}")
    private String authParam;
    private TokenBlz token;
    private String scheme = "https";
    private String authority = ".api.blizzard.com";
    private String locale = "locale=fr_FR";

    protected String invokeBlizzardApi(URI uri) throws BlizzardException {
        StringBuilder content = new StringBuilder();
        try {
            if (token == null) {
                retrieveNewToken();
            }
            URL url = new URL(uri.toASCIIString() + "&access_token=" + token.getToken());
            log.debug("Call to {}", url.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            if (con.getResponseCode() != 200) {
                throw new BlizzardException(con.getResponseCode(), con.getResponseMessage());
            } else {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                log.debug("Response received: {}", content.toString());
            }
            con.disconnect();
        } catch (BlizzardException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error call bnet");
        }
        return content.toString();
    }

    @Scheduled(fixedDelay = 600_000)
    public void isTokenValid() throws Exception {
        log.debug("Check if Blizzard Token for API is still valid");
        boolean isTokenValid = false;
        if (token != null) {
            StringBuilder content = new StringBuilder();
            URL url = new URL("https://eu.battle.net/oauth/check_token?token=" + token.getToken());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            isTokenValid = !content.toString().contains("invalid_token");
        }
        log.debug("Token validity: " + isTokenValid);
        if (!isTokenValid) {
            retrieveNewToken();
        }
    }

    private void retrieveNewToken() throws Exception {
        log.debug("Retrieving new Token for Blizzard");
        StringBuilder content = new StringBuilder();
        URL url = new URL("https://eu.battle.net/oauth/token?grant_type=client_credentials");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Basic " + authParam);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        token = new Gson().fromJson(content.toString(), TokenBlz.class);
    }

}
