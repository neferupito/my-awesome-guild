package io.neferupito.myawesomeguild.core.blizzard.client;

import com.google.gson.Gson;
import io.neferupito.myawesomeguild.core.blizzard.client.exception.BlizzardException;
import io.neferupito.myawesomeguild.core.blizzard.json.WowCharacterBlz;
import io.neferupito.myawesomeguild.data.domain.wow.character.Race;
import io.neferupito.myawesomeguild.data.domain.wow.character.Specialization;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowCharacter;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowClass;
import io.neferupito.myawesomeguild.data.domain.wow.server.Faction;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.domain.wow.server.Region;
import io.neferupito.myawesomeguild.data.repository.user.UserRepository;
import io.neferupito.myawesomeguild.data.repository.wow.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

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
    @Autowired
    private UserRepository userRepository;

    public WowCharacter importNewCharacter(String region, String slugRealm, String name) throws BlizzardException {
            WowCharacterBlz wowCharacterBlz = importCharacter(region, slugRealm, name);

            // TODO: handle null
            Optional<WowCharacter> existingChar = wowCharacterRepository.findById(wowCharacterBlz.getId());
            if (existingChar.isPresent()) {
                return existingChar.get();
            }

            WowCharacter character = transformWowCharacter(region, wowCharacterBlz);
            character = wowCharacterRepository.save(character);

            return character;
    }

    public WowCharacter refreshCharacter(String region, String slugRealm, String name) throws BlizzardException {
        WowCharacterBlz wowCharacterBlz = importCharacter(region, slugRealm, name);
        WowCharacter character = transformWowCharacter(region, wowCharacterBlz);
        character = wowCharacterRepository.save(character);
        return character;
    }

    private WowCharacterBlz importCharacter(String region, String slugRealm, String name) throws BlizzardException {
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

    private WowCharacter transformWowCharacter(String region, WowCharacterBlz json) {
        // TODO: handle nullpointer
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
