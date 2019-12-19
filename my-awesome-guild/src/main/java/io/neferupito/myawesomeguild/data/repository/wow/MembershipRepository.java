package io.neferupito.myawesomeguild.data.repository.wow;

import io.neferupito.myawesomeguild.data.domain.wow.character.WowCharacter;
import io.neferupito.myawesomeguild.data.domain.wow.guild.Guild;
import io.neferupito.myawesomeguild.data.domain.wow.guild.Membership;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MembershipRepository extends CrudRepository<Membership, Long> {

//    @Query("SELECT a FROM Article a WHERE a.title=:title and a.category=:category")
//    List<GuildEntity> findGuildBy(@Param("region") String region, @Param("category") String category);

    List<Membership> findAllByGuildId(Long guildId);

    List<Membership> findAllByGuild(Guild guild);

    Membership findByWowCharacter(WowCharacter wowCharacter);

}
