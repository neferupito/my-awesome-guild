package io.neferupito.myawesomeguild.core.service.wow;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.Thread.sleep;

@Service
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

    public List<Membership> findAllMembersFromGuild(Long guildId) throws AwesomeException {
        List<Membership> members;
//        members = membershipRepository.findAllByGuildId(guildId);
//        System.err.println("**** " + members.size());
        members = membershipRepository.findAllByGuild(findGuildById(guildId));
        System.err.println("==== " + members.size());
        return members;
    }

    public List<Membership> refreshRoster(String region, Long guildId) throws AwesomeException {
        //TODO: error handling
        Guild guild = findGuildById(guildId);
        RosterBlz roster = blizzardClient.importRoster(region, guild.getRealm().getSlug(), guild.getSlugName());
        for (RosterBlz.Mmb mmb : roster.getMembers()) {
            WowCharacter wowCharacter = wowCharacterService.findWowCharacterById(mmb.getCharacter().getId());
            Optional<Membership> previousMembership = checkIfWowCharacterAlreadyHasAMembership(wowCharacter);
            if (previousMembership.isPresent()) {
                membershipRepository.delete(previousMembership.get());
            }
            membershipRepository.save(transformMembership(region, mmb, guild));
        }
        return findAllMembersFromGuild(guildId);
    }

    public Membership findMembershipByWowCharacter(WowCharacter wowCharacter) {
        return membershipRepository.findByWowCharacter(wowCharacter);
    }

    public Guild importNewWowGuild(String region, WowCharacterBlz wowCharacterBlz) throws AwesomeException {
        return importNewWowGuild(region, wowCharacterBlz.getRealm().getSlug(), createSlugName(wowCharacterBlz.getGuild().getName()));
    }

    public Guild importNewWowGuild(String region, String slugRealm, String name) throws AwesomeException {
        GuildBlz guildBlz = blizzardClient.importGuild(region, slugRealm, createSlugName(name));

        Optional<Guild> existingGuild = guildRepository.findById(guildBlz.getId());
        if (existingGuild.isPresent()) {
            return existingGuild.get();
        }

        Guild newGuild = transformGuild(guildBlz);

        try {
            Thread thread = new Thread(() -> {
                try {
                    sleep(5000);
                    importGuildRoster(region, guildBlz);
                } catch (AwesomeException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            return guildRepository.save(newGuild);
        } catch (Exception e) {
            throw new AwesomeException(HttpStatus.INTERNAL_SERVER_ERROR, "Problème lors de l'entrée dans la DB de la guilde " + guildBlz.getId());
        }
    }

    public void importGuildRoster(String region, GuildBlz guild) throws AwesomeException {
        RosterBlz roster = blizzardClient.importRosterByUrl(guild.getRoster().getHref());
        for (RosterBlz.Mmb mmb : roster.getMembers()) {
            membershipRepository.save(transformMembership(region, mmb, guild));
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

    private Membership transformMembership(String region, RosterBlz.Mmb membership, GuildBlz guildBlz) throws AwesomeException {
        Guild guild = findGuildById(guildBlz.getId());
        return transformMembership(region, membership, guild);
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
                }
            }
        }

        Optional<Membership> existingMembership = checkIfWowCharacterAlreadyHasAMembership(wowCharacter);
        if (existingMembership.isPresent()) {
            Guild previousGuild = existingMembership.get().getGuild();
            refreshRoster(region, previousGuild.getId());
            membershipRepository.delete(existingMembership.get());
        }

        return Membership.builder()
                .wowCharacter(wowCharacter)
                .rank(membership.getRank())
                .guild(guild)
                .build();
    }

    private Optional<Membership> checkIfWowCharacterAlreadyHasAMembership(WowCharacter wowCharacter) {
//        Membership m = wowCharacter.getMembership();
//        System.err.println("membership test 1 = " + m);
        Membership m = findMembershipByWowCharacter(wowCharacter);
        System.err.println("membership test 2 = " + m);
        return Optional.ofNullable(m);
    }

    private String createSlugName(String guildName) {
        guildName = guildName.toLowerCase();
        guildName = guildName.replace(" ", "-");
        guildName = guildName.replace("'", "");
        return guildName;
    }

}
