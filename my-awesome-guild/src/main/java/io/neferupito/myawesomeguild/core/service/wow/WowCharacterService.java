package io.neferupito.myawesomeguild.core.service.wow;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.blizzard.client.CharacterBlizzardClient;
import io.neferupito.myawesomeguild.core.blizzard.json.WowCharacterBlz;
import io.neferupito.myawesomeguild.data.domain.user.User;
import io.neferupito.myawesomeguild.data.domain.wow.character.Race;
import io.neferupito.myawesomeguild.data.domain.wow.character.Specialization;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowCharacter;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowClass;
import io.neferupito.myawesomeguild.data.domain.wow.server.Faction;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.domain.wow.server.Region;
import io.neferupito.myawesomeguild.data.repository.user.UserRepository;
import io.neferupito.myawesomeguild.data.repository.wow.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WowCharacterService {

    @Autowired
    private WowCharacterRepository wowCharacterRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RaceRepository raceRepository;
    @Autowired
    private RealmRepository realmRepository;
    @Autowired
    private WowClassRepository wowClassRepository;
    @Autowired
    private SpecializationRepository specializationRepository;
    @Autowired
    private CharacterBlizzardClient blizzardClient;
    @Autowired
    private WowGuildService wowGuildService;

    public WowCharacter findWowCharacterById(Long wowCharacterId) throws AwesomeException {
        Optional<WowCharacter> wowCharacterOpt = wowCharacterRepository.findById(wowCharacterId);
        if (wowCharacterOpt.isEmpty()) {
            throw new AwesomeException(HttpStatus.NOT_FOUND, "Personnage " + wowCharacterId + " inconnu");
        }
        return wowCharacterOpt.get();
    }

    public WowCharacter importNewWowCharacter(String region, String slugRealm, String name) throws AwesomeException {
        WowCharacterBlz wowCharacterBlz = blizzardClient.importCharacter(region, slugRealm, name);
        Optional<WowCharacter> existingCharacter = wowCharacterRepository.findById(wowCharacterBlz.getId());
        if (existingCharacter.isPresent()) {
            return existingCharacter.get();
        }

        if (wowCharacterBlz.getGuild() != null) {
            Thread thread = new Thread(() -> {
                try {
                    wowGuildService.findGuildById(wowCharacterBlz.getGuild().getId());
                } catch (AwesomeException e) {
                    try {
                        wowGuildService.importNewWowGuildByUrl(wowCharacterBlz.getGuild().getKey().getHref());
                    } catch (AwesomeException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        WowCharacter character = transformWowCharacter(Region.findValue(region), wowCharacterBlz);
        character.setAvatarUrl(blizzardClient.importCharacterMedia(region, slugRealm, name).getAvatarURL());

        try {
            return wowCharacterRepository.save(character);
        } catch (Exception e) {
            throw new AwesomeException(HttpStatus.INTERNAL_SERVER_ERROR, "Problème lors de l'entrée dans la DB du personnage " + wowCharacterBlz.getId());
        }
    }

    public void linkWowCharacterToUser(Long wowCharacterId, String userEmail) throws AwesomeException {

        User user = findUserByEmail(userEmail);
        WowCharacter wowCharacter = findWowCharacterById(wowCharacterId);

        if (wowCharacter.getUser() != null) {
            throw new AwesomeException(HttpStatus.BAD_REQUEST, "Personnage " + wowCharacterId + " est déjà lié à l'utilisateur " + wowCharacter.getUser().getEmail());
        }

        wowCharacter.setUser(user);
        wowCharacter.setClaimed(true);
        wowCharacterRepository.save(wowCharacter);
    }

    public List<WowCharacter> findAllCharactersByUser(String userEmail) throws AwesomeException {
        User user = findUserByEmail(userEmail);
        return wowCharacterRepository.findByUser(user);
    }

    public WowCharacter refreshWowCharacter(Long wowCharacterId) throws AwesomeException {

        WowCharacter wowCharacter = findWowCharacterById(wowCharacterId);
        User user = wowCharacter.getUser();
        boolean isClaimed = wowCharacter.isClaimed();
        WowCharacterBlz wowCharacterBlz = blizzardClient.importCharacter(wowCharacter.getRegion().name(), wowCharacter.getRealm().getSlug(), wowCharacter.getName());
        wowGuildService.refreshWowCharacterMembership(wowCharacter, wowCharacterBlz);
        wowCharacter = transformWowCharacter(wowCharacter.getRegion(), wowCharacterBlz);
        wowCharacter.setUser(user);
        wowCharacter.setClaimed(isClaimed);
        wowCharacter.setLastUpdate(new Date());
        wowCharacter.setAvatarUrl(blizzardClient.importCharacterMedia(wowCharacter.getRegion().name(), wowCharacter.getRealm().getSlug(), wowCharacter.getName()).getAvatarURL());

        try {
            wowCharacter = wowCharacterRepository.save(wowCharacter);
        } catch (Exception e) {
            throw new AwesomeException(HttpStatus.INTERNAL_SERVER_ERROR, "Problème lors de la mise à jour de la base de données pour WowCharacter " + wowCharacterId);
        }

        return wowCharacter;
    }

    public void deleteWowCharacterLink(Long wowCharacterId, String userEmail) throws AwesomeException {
        try {
            WowCharacter wowCharacter = findWowCharacterById(wowCharacterId);
            if (wowCharacter.getUser().getEmail().equalsIgnoreCase(userEmail)) {
                wowCharacter.setUser(null);
                wowCharacter.setClaimed(false);
                wowCharacterRepository.save(wowCharacter);
            }
        } catch (Exception e) {
            throw new AwesomeException(HttpStatus.FORBIDDEN, "Suppression du personnage " + wowCharacterId + " non autorisé");
        }
    }

    private User findUserByEmail(String userEmail) throws AwesomeException {
        Optional<User> userOpt = userRepository.findByEmail(userEmail);
        if (userOpt.isEmpty()) {
            throw new AwesomeException(HttpStatus.NOT_FOUND, "Utilisateur " + userEmail + " inconnu");
        }
        return userOpt.get();
    }

    private WowCharacter transformWowCharacter(Region region, WowCharacterBlz json) {
        Race race = raceRepository.findById(json.getRace().getId()).get();
        Realm realm = realmRepository.findById(json.getRealm().getId()).get();
        WowClass wowClass = wowClassRepository.findById(json.getCharacterClass().getId()).get();
        Specialization spec = specializationRepository.findById(json.getActiveSpec().getId()).get();

        return WowCharacter.builder()
                .id(json.getId())
                .name(json.getName())
                .race(race)
                .level(json.getLevel())
                .region(region)
                .realm(realm)
                .faction(Faction.valueOf(json.getFaction().getType()))
                .wowClass(wowClass)
                .mainSpec(spec)
                .lastUpdate(new Date())
                .isClaimed(false)
                .build();
    }
}
