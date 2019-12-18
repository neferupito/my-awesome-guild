package io.neferupito.myawesomeguild.core.blizzard.client;

import com.google.gson.Gson;
import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.blizzard.json.TokenBlz;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
    private String scheme = "https";
    private String authority = ".api.blizzard.com";
    private String locale = "locale=fr_FR";

    @Autowired
    @Getter
    private BlizzardTokenService tokenService;

    protected String invokeBlizzardApi(URI uri) throws AwesomeException {
        StringBuilder content = new StringBuilder();
        try {
            if (getTokenService().getToken() == null) {
                getTokenService().retrieveNewToken();
            }
            URL url = new URL(uri.toASCIIString() + "&access_token=" + getTokenService().getToken().getToken());
            log.debug("Call to {}", url.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            if (con.getResponseCode() != 200) {
                throw new AwesomeException(HttpStatus.valueOf(con.getResponseCode()), con.getResponseMessage() + " FROM BLIZZARD API");
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
        } catch (AwesomeException e) {
            throw e;
        } catch (Exception e) {
            throw new AwesomeException(HttpStatus.INTERNAL_SERVER_ERROR, "Problème lors de l'appel vers Blizzard API");
        }
        return content.toString();
    }

//    abstract BlizzardTokenService getTokenService();

}
