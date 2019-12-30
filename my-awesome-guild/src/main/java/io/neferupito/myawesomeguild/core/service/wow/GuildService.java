package io.neferupito.myawesomeguild.core.service.wow;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.api.controller.dto.MembershipDto;
import io.neferupito.myawesomeguild.core.blizzard.client.GuildBlizzardClient;
import io.neferupito.myawesomeguild.core.blizzard.json.GuildBlz;
import io.neferupito.myawesomeguild.core.blizzard.json.RosterBlz;
import io.neferupito.myawesomeguild.core.blizzard.json.WowCharacterBlz;
import io.neferupito.myawesomeguild.data.domain.wow.character.Character;
import io.neferupito.myawesomeguild.data.domain.wow.guild.Guild;
import io.neferupito.myawesomeguild.data.domain.wow.guild.GuildAuthConfig;
import io.neferupito.myawesomeguild.data.domain.wow.guild.Membership;
import io.neferupito.myawesomeguild.data.domain.wow.server.Faction;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.repository.wow.GuildAuthConfigRepository;
import io.neferupito.myawesomeguild.data.repository.wow.GuildRepository;
import io.neferupito.myawesomeguild.data.repository.wow.MembershipRepository;
import io.neferupito.myawesomeguild.data.repository.wow.RealmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Thread.sleep;

@Service
@Slf4j
public class GuildService {

    @Autowired
    private GuildBlizzardClient blizzardClient;

    @Autowired
    private GuildRepository guildRepository;
    @Autowired
    private CharacterService characterService;
    @Autowired
    private GuildAuthConfigService guildAuthConfigService;
    @Autowired
    private RealmRepository realmRepository;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private GuildAuthConfigRepository guildAuthConfigRepository;

    public Guild getGuildById(Long guildId) throws AwesomeException {
        Optional<Guild> guild = guildRepository.findById(guildId);
        if (guild.isEmpty()) {
            throw new AwesomeException(HttpStatus.NOT_FOUND, "Guilde " + guildId + " inconnu");
        }
        return guild.get();
    }

    public List<MembershipDto> getGuildRosterIds(Long guildId) throws AwesomeException {
        List<Membership> members;
        List<MembershipDto> dtos = new ArrayList<>();
        members = membershipRepository.findAllByGuild(getGuildById(guildId));
        for (Membership member :
                members) {
            dtos.add(transformMembershipDto(member));
        }
        Collections.sort(dtos);
        return dtos;
    }

    public List<MembershipDto> refreshGuildRoster(Long guildId) throws AwesomeException {
        //TODO: error handling
        Guild guild = getGuildById(guildId);
        RosterBlz roster = blizzardClient.importRoster(guild.getRealm().getRegion().name(), guild.getRealm().getSlug(), guild.getSlugName());
        for (RosterBlz.Mmb mmb : roster.getMembers()) {
            Character character;
            try {
                character = characterService.getCharacterById(mmb.getCharacter().getId());
            } catch (AwesomeException e) {
                if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {
                    character = characterService.importCharacterFromBlizzard(guild.getRealm().getRegion().name(), mmb.getCharacter().getRealm().getSlug(), mmb.getCharacter().getName());
                } else {
                    throw e;
                }
            }

            Optional<Membership> previousMembership = checkIfCharacterHasAlreadyAMembership(character);
            previousMembership.ifPresent(membership -> membershipRepository.delete(membership));
            membershipRepository.save(transformMembership(guild.getRealm().getRegion().name(), mmb, guild));

            if (mmb.getRank() == 0 && !mmb.getCharacter().getId().equals(guild.getAuthConfig().getGuildMaster().getId())) {
                GuildAuthConfig authConfig = guild.getAuthConfig();
                authConfig.setGuildMaster(character);
                guildAuthConfigRepository.save(authConfig);
            }
        }
        return getGuildRosterIds(guildId);
    }

    public Membership getCharacterMembership(Long wowCharacterId) throws AwesomeException {
        Character character = characterService.getCharacterById(wowCharacterId);
        return membershipRepository.findByCharacter(character);
    }

    public Membership getCharacterMembership(Character character) {
        return membershipRepository.findByCharacter(character);
    }

    public Guild importGuildFromBlizzardByUrl(String url) throws AwesomeException {
        GuildBlz guildBlz = blizzardClient.importGuildByUrl(url + "&locale=fr_FR");

        Optional<Guild> existingGuild = guildRepository.findById(guildBlz.getId());
        if (existingGuild.isPresent()) {
            return existingGuild.get();
        }

        Guild newGuild = transformGuildBlz(guildBlz);
        try {
            guildRepository.save(newGuild);
            Thread thread = new Thread(() -> {
                try {
                    sleep(2000);
                    importGuildRoster(newGuild.getRealm().getRegion().name(), guildBlz, newGuild);
                } catch (AwesomeException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            return newGuild;
        } catch (Exception e) {
            throw new AwesomeException(HttpStatus.INTERNAL_SERVER_ERROR, "Problème lors de l'entrée dans la DB de la guilde " + guildBlz.getId());
        }
    }

    public Guild importGuildFromBlizzard(String region, String slugRealm, String name) throws AwesomeException {
        GuildBlz guildBlz = blizzardClient.importGuild(region, slugRealm, getSlugName(name));

        Optional<Guild> existingGuild = guildRepository.findById(guildBlz.getId());
        if (existingGuild.isPresent()) {
            return existingGuild.get();
        }

        Guild newGuild = transformGuildBlz(guildBlz);
        try {
            guildRepository.save(newGuild);
            Thread thread = new Thread(() -> {
                try {
                    sleep(2000);
                    importGuildRoster(region, guildBlz, newGuild);
                } catch (AwesomeException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            return newGuild;
        } catch (Exception e) {
            throw new AwesomeException(HttpStatus.INTERNAL_SERVER_ERROR, "Problème lors de l'entrée dans la DB de la guilde " + guildBlz.getId());
        }
    }

    public void importGuildRoster(String region, GuildBlz guildBlz, Guild guild) throws AwesomeException {
        RosterBlz roster = blizzardClient.importRosterByUrl(guildBlz.getRoster().getHref());
        for (RosterBlz.Mmb mmb : roster.getMembers()) {
            log.debug("Creating membership for " + mmb.getCharacter().getName());
            Membership m = transformMembership(region, mmb, guild);
            if (m != null) {
                if (m.getRank() == 0) {
                    guildAuthConfigService.addGuildAuthConfig(guild.getId(),
                            GuildAuthConfig.builder()
                                    .guildMaster(m.getCharacter())
                                    .build());
                }
                membershipRepository.save(m);
            }
        }
    }

    public void refreshCharacterMembership(Character oldCharacter, WowCharacterBlz newWowCharacter) throws AwesomeException {
        Membership membership = getCharacterMembership(oldCharacter);
        if (membership == null && newWowCharacter.getGuild() != null) {
            importGuildFromBlizzardByUrl(newWowCharacter.getGuild().getKey().getHref());
        }
    }

    public Character getGuildMaster(Long guildId) throws AwesomeException {
        Guild guild = getGuildById(guildId);
        return guild.getAuthConfig().getGuildMaster();
    }

    private Guild transformGuildBlz(GuildBlz guildBlz) {
        Realm realm = realmRepository.findById(guildBlz.getRealm().getId()).get();
        return Guild.builder()
                .id(guildBlz.getId())
                .name(guildBlz.getName())
                .slugName(getSlugName(guildBlz.getName()))
                .faction(Faction.valueOf(guildBlz.getFaction().getType()))
                .realm(realm)
                .lastUpdate(new Date())
                .build();
    }

    private Membership transformMembership(String region, RosterBlz.Mmb membership, Guild guild) throws AwesomeException {
        // TODO: review error handling
        Character character = null;
        try {
            character = characterService.getCharacterById(membership.getCharacter().getId());
        } catch (AwesomeException e) {
            if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {
                try {
                    character = characterService.importCharacterFromBlizzard(region, membership.getCharacter().getRealm().getSlug(), membership.getCharacter().getName());
                } catch (AwesomeException ex) {
                    ex.printStackTrace();
                    return null;
                }
            }
        }

        Optional<Membership> existingMembership = checkIfCharacterHasAlreadyAMembership(character);
        if (existingMembership.isPresent()) {
            Guild previousGuild = existingMembership.get().getGuild();
            refreshGuildRoster(previousGuild.getId());
            membershipRepository.delete(existingMembership.get());
        }

        return Membership.builder()
                .character(character)
                .rank(membership.getRank())
                .guild(guild)
                .build();
    }

    private Optional<Membership> checkIfCharacterHasAlreadyAMembership(Character character) {
        Membership membership = getCharacterMembership(character);
        return Optional.ofNullable(membership);
    }

    private String getSlugName(String guildName) {
        if (!guildName.contains("-")) {
            guildName = guildName.toLowerCase();
            guildName = guildName.replace("'", "");
            guildName = guildName.replace("-", "");
            guildName = guildName.replace(" ", "-");
        }
        return guildName;
    }

    private MembershipDto transformMembershipDto(Membership membership) {
        return MembershipDto.builder()
                .wowCharacterId(membership.getCharacter().getId())
                .rank(membership.getRank())
                .guildId(membership.getGuild().getId())
                .build();
    }

}
