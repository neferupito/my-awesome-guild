package io.neferupito.myawesomeguild.core.blizzard.client;

import com.google.gson.Gson;
import io.neferupito.myawesomeguild.core.blizzard.json.RaceBlz;
import io.neferupito.myawesomeguild.core.blizzard.json.RealmBlz;
import io.neferupito.myawesomeguild.core.blizzard.json.SpecializationBlz;
import io.neferupito.myawesomeguild.core.blizzard.json.SpecializationDetailsBlz;
import io.neferupito.myawesomeguild.data.domain.user.User;
import io.neferupito.myawesomeguild.data.domain.wow.character.Race;
import io.neferupito.myawesomeguild.data.domain.wow.character.Role;
import io.neferupito.myawesomeguild.data.domain.wow.character.Specialization;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowClass;
import io.neferupito.myawesomeguild.data.domain.wow.server.Faction;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.domain.wow.server.Region;
import io.neferupito.myawesomeguild.data.repository.user.UserRepository;
import io.neferupito.myawesomeguild.data.repository.wow.RaceRepository;
import io.neferupito.myawesomeguild.data.repository.wow.RealmRepository;
import io.neferupito.myawesomeguild.data.repository.wow.SpecializationRepository;
import io.neferupito.myawesomeguild.data.repository.wow.WowClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Slf4j
public class ConstantDataImportBlizzardClient extends BlizzardClient {

    @Autowired
    private RealmRepository realmRepository;
    @Autowired
    private RaceRepository raceRepository;
    @Autowired
    private WowClassRepository wowClassRepository;
    @Autowired
    private SpecializationRepository specializationRepository;
    @Autowired
    private UserRepository userRepository;

    public void importAllData() {
        userRepository.save(User.builder()
                .email("a@a.a")
                .build());

        importRaces();
        importSpecs();
        for (Specialization spec :
                specializationRepository.findAll()) {
            importSpecDetails(spec);
        }
        for (Region region :
                Region.values()) {
            importRealms(region);
        }
    }

    private void importRealms(Region region) {
        log.debug("{} WoW realms import from Blizzard", region);
        try {
            String path = "/data/wow/realm/index";
            URI uri = new URI(
                    getScheme(),
                    region.toString().toLowerCase() + getAuthority(),
                    path,
                    getLocale() + "&" + "namespace=dynamic-" + region.toString().toLowerCase(),
                    null);
            String response = invokeBlizzardApi(uri);
            RealmBlz realmBlz = new Gson().fromJson(response, RealmBlz.class);
            for (RealmBlz.Rlm rlm :
                    realmBlz.getRealms()) {
                realmRepository.save(Realm.builder()
                        .id(rlm.getId())
                        .region(region)
                        .name(rlm.getName())
                        .slug(rlm.getSlug())
                        .build());
            }
        } catch (URISyntaxException e) {
            log.error("erreur call Bnet", e);
        }
    }

    private void importRaces() {
        log.debug("WoW races import from Blizzard");
        try {
            String path = "/wow/data/character/races";
            URI uri = new URI(
                    getScheme(),
                    "eu" + getAuthority(),
                    path,
                    getLocale(),
                    null);
            String response = invokeBlizzardApi(uri);
            RaceBlz realmBlz = new Gson().fromJson(response, RaceBlz.class);
            for (RaceBlz.Rc rc :
                    realmBlz.getRaces()) {
                raceRepository.save(Race.builder()
                        .id(rc.getId())
                        .faction(Faction.valueOf(rc.getSide().toUpperCase()))
                        .name(rc.getName())
                        .build());
            }
        } catch (URISyntaxException e) {
            log.error("erreur call Bnet", e);
        }
    }

    private void importSpecs() {
        log.debug("WoW classes and specs import from Blizzard");
        try {
            String path = "/data/wow/playable-specialization/index";
            URI uri = new URI(
                    getScheme(),
                    "eu" + getAuthority(),
                    path,
                    "namespace=static-eu" + "&" + getLocale(),
                    null);
            String response = invokeBlizzardApi(uri);
            SpecializationBlz specializationBlz = new Gson().fromJson(response, SpecializationBlz.class);
            for (SpecializationBlz.Spec spec :
                    specializationBlz.getCharacterSpecializations()) {
                specializationRepository.save(Specialization.builder()
                        .id(spec.getId())
                        .name(spec.getName())
                        .build());
            }
        } catch (URISyntaxException e) {
            log.error("erreur call Bnet", e);
        }
    }

    private void importSpecDetails(Specialization spec) {
        log.debug("Details for spec {} import from Blizzard", spec.getName());
        try {
            String path = "/data/wow/playable-specialization/" + spec.getId();
            URI uri = new URI(
                    getScheme(),
                    "eu" + getAuthority(),
                    path,
                    "namespace=static-eu" + "&" + getLocale(),
                    null);
            String response = invokeBlizzardApi(uri);
            SpecializationDetailsBlz specializationDetailsBlz = new Gson().fromJson(response, SpecializationDetailsBlz.class);

            WowClass wowClass = wowClassRepository.save(WowClass.builder()
                    .id(specializationDetailsBlz.getWowClass().getId())
                    .name(specializationDetailsBlz.getWowClass().getName())
                    .build());

            spec.setWowClass(wowClass);
            spec.setRole(Role.valueOf(specializationDetailsBlz.getRole().getType()));

            specializationRepository.save(spec);
        } catch (URISyntaxException e) {
            log.error("erreur call Bnet", e);
        }
    }

}
