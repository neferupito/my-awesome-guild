package io.neferupito.myawesomeguild.core.service.wow;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.blizzard.client.CharacterBlizzardClient;
import io.neferupito.myawesomeguild.core.blizzard.json.WowCharacterBlz;
import io.neferupito.myawesomeguild.data.domain.user.User;
import io.neferupito.myawesomeguild.data.domain.wow.character.Character;
import io.neferupito.myawesomeguild.data.domain.wow.character.Race;
import io.neferupito.myawesomeguild.data.domain.wow.character.Specialization;
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
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;
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
    private GuildService guildService;

    public Character getCharacterById(Long wowCharacterId) throws AwesomeException {
        Optional<Character> wowCharacterOpt = characterRepository.findById(wowCharacterId);
        if (wowCharacterOpt.isEmpty()) {
            throw new AwesomeException(HttpStatus.NOT_FOUND, "Personnage " + wowCharacterId + " inconnu");
        }
        return wowCharacterOpt.get();
    }

    public Character importCharacterFromBlizzard(String region, String slugRealm, String name) throws AwesomeException {
        WowCharacterBlz wowCharacterBlz = blizzardClient.importCharacter(region, slugRealm, name);
        Optional<Character> existingCharacter = characterRepository.findById(wowCharacterBlz.getId());
        if (existingCharacter.isPresent()) {
            return existingCharacter.get();
        }

        if (wowCharacterBlz.getGuild() != null) {
            Thread thread = new Thread(() -> {
                try {
                    guildService.getGuildById(wowCharacterBlz.getGuild().getId());
                } catch (AwesomeException e) {
                    try {
                        guildService.importGuildFromBlizzardByUrl(wowCharacterBlz.getGuild().getKey().getHref());
                    } catch (AwesomeException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        Character character = transformCharacterBlz(Region.findValue(region), wowCharacterBlz);
        character.setAvatarUrl(blizzardClient.importCharacterMedia(region, slugRealm, name).getAvatarURL());

        try {
            return characterRepository.save(character);
        } catch (Exception e) {
            throw new AwesomeException(HttpStatus.INTERNAL_SERVER_ERROR, "Problème lors de l'entrée dans la DB du personnage " + wowCharacterBlz.getId());
        }
    }

    public void linkCharacterToUser(Long wowCharacterId, String userEmail) throws AwesomeException {

        User user = getUserByEmail(userEmail);
        Character character = getCharacterById(wowCharacterId);

        if (character.getUser() != null) {
            throw new AwesomeException(HttpStatus.BAD_REQUEST, "Personnage " + wowCharacterId + " est déjà lié à l'utilisateur " + character.getUser().getEmail());
        }

        character.setUser(user);
        character.setClaimed(true);
        characterRepository.save(character);
    }

    public List<Character> getAllCharactersOfAnUser(String userEmail) throws AwesomeException {
        User user = getUserByEmail(userEmail);
        return characterRepository.findByUser(user);
    }

    public Character refreshCharacter(Long wowCharacterId) throws AwesomeException {

        Character character = getCharacterById(wowCharacterId);
        User user = character.getUser();
        boolean isClaimed = character.isClaimed();
        WowCharacterBlz wowCharacterBlz = blizzardClient.importCharacter(character.getRegion().name(), character.getRealm().getSlug(), character.getName());
        guildService.refreshCharacterMembership(character, wowCharacterBlz);
        character = transformCharacterBlz(character.getRegion(), wowCharacterBlz);
        character.setUser(user);
        character.setClaimed(isClaimed);
        character.setLastUpdate(new Date());
        character.setAvatarUrl(blizzardClient.importCharacterMedia(character.getRegion().name(), character.getRealm().getSlug(), character.getName()).getAvatarURL());

        try {
            character = characterRepository.save(character);
        } catch (Exception e) {
            throw new AwesomeException(HttpStatus.INTERNAL_SERVER_ERROR, "Problème lors de la mise à jour de la base de données pour WowCharacter " + wowCharacterId);
        }

        return character;
    }

    public void deleteCharacterLink(Long wowCharacterId, String userEmail) throws AwesomeException {
        try {
            Character character = getCharacterById(wowCharacterId);
            if (character.getUser().getEmail().equalsIgnoreCase(userEmail)) {
                character.setUser(null);
                character.setClaimed(false);
                characterRepository.save(character);
            }
        } catch (Exception e) {
            throw new AwesomeException(HttpStatus.FORBIDDEN, "Suppression du personnage " + wowCharacterId + " non autorisé");
        }
    }

    private User getUserByEmail(String userEmail) throws AwesomeException {
        Optional<User> userOpt = userRepository.findByEmail(userEmail);
        if (userOpt.isEmpty()) {
            throw new AwesomeException(HttpStatus.NOT_FOUND, "Utilisateur " + userEmail + " inconnu");
        }
        return userOpt.get();
    }

    private Character transformCharacterBlz(Region region, WowCharacterBlz json) {
        Race race = raceRepository.findById(json.getRace().getId()).get();
        Realm realm = realmRepository.findById(json.getRealm().getId()).get();
        WowClass wowClass = wowClassRepository.findById(json.getCharacterClass().getId()).get();
        Specialization spec = specializationRepository.findById(json.getActiveSpec().getId()).get();

        return Character.builder()
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
