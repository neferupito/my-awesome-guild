package io.neferupito.myawesomeguild.core.blizzard.client;

import com.google.gson.Gson;
import io.neferupito.myawesomeguild.core.blizzard.json.WowCharacterBlz;
import io.neferupito.myawesomeguild.data.domain.wow.character.Race;
import io.neferupito.myawesomeguild.data.domain.wow.character.Specialization;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowCharacter;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowClass;
import io.neferupito.myawesomeguild.data.domain.wow.server.Faction;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.domain.wow.server.Region;
import io.neferupito.myawesomeguild.data.repository.wow.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
public class CharacterBlizzardClient extends BlizzardClient {

    @Autowired
    private RaceRepository raceRepository;
    @Autowired
    private RealmRepository realmRepository;
    @Autowired
    private WowClassRepository wowClassRepository;
    @Autowired
    private SpecializationRepository specializationRepository;
    @Autowired
    private WowCharacterRepository wowCharacterRepository;

    public WowCharacter importCharacter(String region, String slugRealm, String name) {
//        https://eu.api.blizzard.com/profile/wow/character/hyjal/harà?namespace=profile-eu&locale=fr_FR&access_token=EUoT9NgZw6RKuYdNbr7NpV1r11fvK9VIdJ

//        log.debug("{} WoW realms import from Blizzard", region);
        try {
            String path = "/profile/wow/character/" + slugRealm.toLowerCase() + "/" + name.toLowerCase();
            URI uri = new URI(
                    getScheme(),
                    region.toLowerCase() + getAuthority(),
                    path,
                    getLocale() + "&" + "namespace=profile-" + region.toLowerCase(),
                    null);
            String response = invokeBlizzardApi(uri);
            WowCharacterBlz wowCharacterBlz = new Gson().fromJson(response, WowCharacterBlz.class);

            WowCharacter character = transformWowCharacter(region, wowCharacterBlz);

            character = wowCharacterRepository.save(character);
            return character;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            log.error("erreur call Bnet", e);
        }
        return null;
    }

    private WowCharacter transformWowCharacter(String region, WowCharacterBlz json) {
        Race race = raceRepository.findById(json.getRace().getId()).get();
        Realm realm = realmRepository.findById(json.getRealm().getId()).get();
        WowClass wowClass = wowClassRepository.findById(json.getCharacterClass().getId()).get();
        Specialization spec = specializationRepository.findById(json.getActiveSpec().getId()).get();

        WowCharacter character = WowCharacter.builder()
                .id(json.getId())
                .name(json.getName())
                .race(race)
                .level(json.getLevel())
                .region(Region.valueOf(region.toUpperCase()))
                .realm(realm)
                .faction(Faction.valueOf(json.getFaction().getType()))
                .wowClass(wowClass)
                .mainSpec(spec).build();
        return character;
    }

}
