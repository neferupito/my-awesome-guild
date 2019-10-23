package io.neferupito.myawesomeguild.data.repository.wow;

import io.neferupito.myawesomeguild.data.domain.wow.guild.Membership;
import org.springframework.data.repository.CrudRepository;

public interface MembershipRepository extends CrudRepository<Membership, Long> {

//    @Query("SELECT a FROM Article a WHERE a.title=:title and a.category=:category")
//    List<GuildEntity> findGuildBy(@Param("region") String region, @Param("category") String category);

}
