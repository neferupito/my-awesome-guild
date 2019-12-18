package io.neferupito.myawesomeguild.core.service.wow;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.blizzard.client.GuildBlizzardClient;
import io.neferupito.myawesomeguild.core.blizzard.json.GuildBlz;
import io.neferupito.myawesomeguild.data.domain.wow.guild.Guild;
import io.neferupito.myawesomeguild.data.domain.wow.server.Faction;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.repository.user.UserRepository;
import io.neferupito.myawesomeguild.data.repository.wow.GuildRepository;
import io.neferupito.myawesomeguild.data.repository.wow.RealmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WowGuildService {

    @Autowired
    private GuildBlizzardClient blizzardClient;

    @Autowired
    private GuildRepository guildRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RealmRepository realmRepository;

    public Guild importNewWowGuild(String region, String slugRealm, String name) throws AwesomeException {

        GuildBlz guildBlz = blizzardClient.importGuild(region, slugRealm, createSlugName(name));

        //TODO: add check if guild exists

        Guild newGuild = transformGuild(guildBlz);
        newGuild = guildRepository.save(newGuild);

        return newGuild;
//        WowCharacterBlz wowCharacterBlz = blizzardClient.importCharacter(region, slugRealm, name);
//        Optional<WowCharacter> existingCharacter = wowCharacterRepository.findById(wowCharacterBlz.getId());
//        if (existingCharacter.isPresent()) {
//            return existingCharacter.get();
//        }
//        WowCharacter character = transformWowCharacter(Region.valueOf(region), wowCharacterBlz);
//        character.setAvatarUrl(blizzardClient.importCharacterMedia(region, slugRealm, name).getAvatarURL());
//
//        try {
//            return wowCharacterRepository.save(character);
//        } catch (Exception e) {
//            throw new AwesomeException(HttpStatus.INTERNAL_SERVER_ERROR, "Problème lors de l'entrée dans la DB du personnage " + wowCharacterBlz.getId());
//        }
    }

    private Guild transformGuild(GuildBlz guildBlz) {
        Realm realm = realmRepository.findById(guildBlz.getRealm().getId()).get();
        return Guild.builder()
                .id(guildBlz.getId())
                .name(guildBlz.getName())
                .slugName(createSlugName(guildBlz.getName()))
                .faction(Faction.valueOf(guildBlz.getFaction().getType()))
                .realm(realm)
                .lastUpdate(new Date())
                .build();
    }

    private String createSlugName(String guildName) {
        guildName = guildName.toLowerCase();
        guildName = guildName.replace(" ", "-");
        guildName = guildName.replace("'", "");
        return guildName;
    }

//    public WowCharacter refreshWowGuild(Long wowCharacterId) throws AwesomeException {

//        WowCharacter wowCharacter = findWowCharacterById(wowCharacterId);
//        User user = wowCharacter.getUser();
//        WowCharacterBlz wowCharacterBlz = blizzardClient.importCharacter(wowCharacter.getRegion().name(), wowCharacter.getRealm().getSlug(), wowCharacter.getName());
//        wowCharacter = transformWowCharacter(wowCharacter.getRegion(), wowCharacterBlz);
//        wowCharacter.setUser(user);
//        wowCharacter.setLastUpdate(new Date());
//        wowCharacter.setAvatarUrl(blizzardClient.importCharacterMedia(wowCharacter.getRegion().name(), wowCharacter.getRealm().getSlug(), wowCharacter.getName()).getAvatarURL());
//
//        try {
//            wowCharacter = wowCharacterRepository.save(wowCharacter);
//        } catch (Exception e) {
//            throw new AwesomeException(HttpStatus.INTERNAL_SERVER_ERROR, "Problème lors de la mise à jour de la base de données pour WowCharacter " + wowCharacterId);
//        }
//
//        return wowCharacter;
//    }

//    private User findUserByEmail(String userEmail) throws AwesomeException {
//        Optional<User> userOpt = userRepository.findByEmail(userEmail);
//        if (userOpt.isEmpty()) {
//            throw new AwesomeException(HttpStatus.NOT_FOUND, "Utilisateur " + userEmail + " inconnu");
//        }
//        return userOpt.get();
//    }

}
