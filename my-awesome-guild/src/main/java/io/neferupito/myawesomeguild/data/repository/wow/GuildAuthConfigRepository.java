package io.neferupito.myawesomeguild.data.repository.wow;

import io.neferupito.myawesomeguild.data.domain.wow.guild.Guild;
import io.neferupito.myawesomeguild.data.domain.wow.guild.GuildAuthConfig;
import org.springframework.data.repository.CrudRepository;

public interface GuildAuthConfigRepository extends CrudRepository<GuildAuthConfig, Long> {

//    @Query("SELECT a FROM Article a WHERE a.title=:title and a.category=:category")
//    List<GuildEntity> findGuildBy(@Param("region") String region, @Param("category") String category);

}
