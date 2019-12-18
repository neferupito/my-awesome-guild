package io.neferupito.myawesomeguild.core.blizzard.client;

import com.google.gson.Gson;
import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.blizzard.json.WowCharacterBlz;
import io.neferupito.myawesomeguild.core.blizzard.json.WowCharacterMediaBlz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
public class CharacterBlizzardClient extends BlizzardClient {

    public WowCharacterBlz importCharacter(String region, String slugRealm, String name) throws AwesomeException {
        try {
            String path = "/profile/wow/character/" + slugRealm.toLowerCase() + "/" + name.toLowerCase();
            URI uri = new URI(
                    getScheme(),
                    region.toLowerCase() + getAuthority(),
                    path,
                    getLocale() + "&" + "namespace=profile-" + region.toLowerCase(),
                    null);
            String response = invokeBlizzardApi(uri);
            return new Gson().fromJson(response, WowCharacterBlz.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.error("erreur call Bnet", e);
        }
        return null;
    }

    public WowCharacterMediaBlz importCharacterMedia(String region, String slugRealm, String name) throws AwesomeException {
        //https://eu.api.blizzard.com/profile/wow/character/hyjal/hàrà/character-media?namespace=profile-eu&locale=fr_FR&access_token=EUmZEjB7Z8prKBQhIqjpCZj9z4nDW62tZ1
        try {
            String path = "/profile/wow/character/" + slugRealm.toLowerCase() + "/" + name.toLowerCase() + "/character-media";
            URI uri = new URI(
                    getScheme(),
                    region.toLowerCase() + getAuthority(),
                    path,
                    getLocale() + "&" + "namespace=profile-" + region.toLowerCase(),
                    null);
            String response = invokeBlizzardApi(uri);
            return new Gson().fromJson(response, WowCharacterMediaBlz.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.error("erreur call Bnet", e);
        }
        return null;
    }

}
