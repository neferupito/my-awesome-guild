package io.neferupito.myawesomeguild.core.blizzard.client;

import com.google.gson.Gson;
import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.blizzard.json.TokenBlz;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
@Getter
public class BlizzardTokenService {

    @Value("${blizzard.oauth.authorization}")
    private String authParam;
    private TokenBlz token;

    public void retrieveNewToken() throws Exception {
        log.debug("Retrieving new Token for Blizzard");
        StringBuilder content = new StringBuilder();
        URL url = new URL("https://eu.battle.net/oauth/token?grant_type=client_credentials");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Basic " + authParam);
        if (con.getResponseCode() != 200) {
            throw new AwesomeException(HttpStatus.INTERNAL_SERVER_ERROR, "Problème lors de l'appel pour un nouveau token");
        }
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        token = new Gson().fromJson(content.toString(), TokenBlz.class);
        System.err.println("new Token: " + token.getToken());
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

}
