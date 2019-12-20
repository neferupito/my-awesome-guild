package io.neferupito.myawesomeguild.core.service.wow;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.api.controller.dto.MembershipDto;
import io.neferupito.myawesomeguild.core.blizzard.client.GuildBlizzardClient;
import io.neferupito.myawesomeguild.core.blizzard.json.GuildBlz;
import io.neferupito.myawesomeguild.core.blizzard.json.RosterBlz;
import io.neferupito.myawesomeguild.core.blizzard.json.WowCharacterBlz;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowCharacter;
import io.neferupito.myawesomeguild.data.domain.wow.guild.Guild;
import io.neferupito.myawesomeguild.data.domain.wow.guild.Membership;
import io.neferupito.myawesomeguild.data.domain.wow.server.Faction;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
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
public class WowGuildService {

    @Autowired
    private GuildBlizzardClient blizzardClient;

    @Autowired
    private GuildRepository guildRepository;
    @Autowired
    private WowCharacterService wowCharacterService;
    @Autowired
    private RealmRepository realmRepository;
    @Autowired
    private MembershipRepository membershipRepository;

    public Guild findGuildById(Long guildId) throws AwesomeException {
        Optional<Guild> guild = guildRepository.findById(guildId);
        if (guild.isEmpty()) {
            throw new AwesomeException(HttpStatus.NOT_FOUND, "Guilde " + guildId + " inconnu");
        }
        return guild.get();
    }

    public List<MembershipDto> findAllMembersFromGuild(Long guildId) throws AwesomeException {
        List<Membership> members;
        List<MembershipDto> dtos = new ArrayList<>();
//        members = membershipRepository.findAllByGuildId(guildId);
//        System.err.println("**** " + members.size());
        members = membershipRepository.findAllByGuild(findGuildById(guildId));
        for (Membership member :
                members) {
            dtos.add(transformMembershipDto(member));
        }
        Collections.sort(dtos);
        return dtos;
    }

    public List<MembershipDto> refreshRoster(Long guildId) throws AwesomeException {
        //TODO: error handling
        Guild guild = findGuildById(guildId);
        RosterBlz roster = blizzardClient.importRoster(guild.getRealm().getRegion().name(), guild.getRealm().getSlug(), guild.getSlugName());
        for (RosterBlz.Mmb mmb : roster.getMembers()) {
            WowCharacter wowCharacter;
            try {
                wowCharacter = wowCharacterService.findWowCharacterById(mmb.getCharacter().getId());
            } catch (AwesomeException e) {
                if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {
                    wowCharacter = wowCharacterService.importNewWowCharacter(guild.getRealm().getRegion().name(), mmb.getCharacter().getRealm().getSlug(), mmb.getCharacter().getName());
                } else {
                    throw e;
                }
            }

            Optional<Membership> previousMembership = checkIfWowCharacterAlreadyHasAMembership(wowCharacter);
            previousMembership.ifPresent(membership -> membershipRepository.delete(membership));
            membershipRepository.save(transformMembership(guild.getRealm().getRegion().name(), mmb, guild));
        }
        return findAllMembersFromGuild(guildId);
    }

    public Membership findMembershipByWowCharacter(WowCharacter wowCharacter) {
        return membershipRepository.findByWowCharacter(wowCharacter);
    }

    public Guild importNewWowGuildByUrl(String url) throws AwesomeException {
        GuildBlz guildBlz = blizzardClient.importGuildByUrl(url + "&locale=fr_FR");

        Optional<Guild> existingGuild = guildRepository.findById(guildBlz.getId());
        if (existingGuild.isPresent()) {
            return existingGuild.get();
        }

        Guild newGuild = transformGuild(guildBlz);
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

    public Guild importNewWowGuild(String region, String slugRealm, String name) throws AwesomeException {
        GuildBlz guildBlz = blizzardClient.importGuild(region, slugRealm, createSlugName(name));

        Optional<Guild> existingGuild = guildRepository.findById(guildBlz.getId());
        if (existingGuild.isPresent()) {
            return existingGuild.get();
        }

        Guild newGuild = transformGuild(guildBlz);
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
                membershipRepository.save(m);
            }
        }

    }

    public void refreshWowCharacterMembership(WowCharacter oldWowCharacter, WowCharacterBlz newWowCharacter) throws AwesomeException {
        Membership membership = findMembershipByWowCharacter(oldWowCharacter);
        if (membership == null && newWowCharacter.getGuild() != null) {
            importNewWowGuildByUrl(newWowCharacter.getGuild().getKey().getHref());
        }
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

    private Membership transformMembership(String region, RosterBlz.Mmb membership, Guild guild) throws AwesomeException {
        // TODO: review error handling
        WowCharacter wowCharacter = null;
        try {
            wowCharacter = wowCharacterService.findWowCharacterById(membership.getCharacter().getId());
        } catch (AwesomeException e) {
            if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {
                try {
                    wowCharacter = wowCharacterService.importNewWowCharacter(region, membership.getCharacter().getRealm().getSlug(), membership.getCharacter().getName());
                } catch (AwesomeException ex) {
                    ex.printStackTrace();
                    return null;
                }
            }
        }

        Optional<Membership> existingMembership = checkIfWowCharacterAlreadyHasAMembership(wowCharacter);
        if (existingMembership.isPresent()) {
            Guild previousGuild = existingMembership.get().getGuild();
            refreshRoster(previousGuild.getId());
            membershipRepository.delete(existingMembership.get());
        }

        return Membership.builder()
                .wowCharacter(wowCharacter)
                .rank(membership.getRank())
                .guild(guild)
                .build();
    }

    private Optional<Membership> checkIfWowCharacterAlreadyHasAMembership(WowCharacter wowCharacter) {
        Membership membership = findMembershipByWowCharacter(wowCharacter);
        return Optional.ofNullable(membership);
    }

    private String createSlugName(String guildName) {
        guildName = guildName.toLowerCase();
        guildName = guildName.replace("'", "");
        guildName = guildName.replace("-", "");
        guildName = guildName.replace(" ", "-");
        return guildName;
    }

    private MembershipDto transformMembershipDto(Membership membership) {
        return MembershipDto.builder()
                .wowCharacterId(membership.getWowCharacter().getId())
                .rank(membership.getRank())
                .build();
    }

}
