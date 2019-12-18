package io.neferupito.myawesomeguild.core.blizzard.client;

import com.google.gson.Gson;
import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.blizzard.json.GuildBlz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
public class GuildBlizzardClient extends BlizzardClient {

    public GuildBlz importGuild(String region, String slugRealm, String slugName) throws AwesomeException {
//        https://eu.api.blizzard.com/data/wow/guild/hyjal/arkham?namespace=profile-eu&locale=fr_FR&access_token=EUmZEjB7Z8prKBQhIqjpCZj9z4nDW62tZ1

        try {
            String path = "/data/wow/guild/" + slugRealm.toLowerCase() + "/" + slugName;
            URI uri = new URI(
                    getScheme(),
                    region.toLowerCase() + getAuthority(),
                    path,
                    getLocale() + "&" + "namespace=profile-" + region.toLowerCase(),
                    null);
            String response = invokeBlizzardApi(uri);
            return new Gson().fromJson(response, GuildBlz.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.error("erreur call Bnet", e);
        }
        return null;
    }

    public GuildBlz importGuildByUrl(String url) throws AwesomeException {
        try {
            String response = invokeBlizzardApi(new URI(url));
            return new Gson().fromJson(response, GuildBlz.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.error("erreur call Bnet", e);
        }
        return null;
    }


}
