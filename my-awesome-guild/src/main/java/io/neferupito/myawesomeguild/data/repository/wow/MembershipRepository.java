package io.neferupito.myawesomeguild.data.repository.recruitment.wow;

import io.neferupito.myawesomeguild.data.domain.wow.guild.Guild;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GuildRepository extends CrudRepository<Guild, Long> {

//    @Query("SELECT a FROM Article a WHERE a.title=:title and a.category=:category")
//    List<GuildEntity> findGuildBy(@Param("region") String region, @Param("category") String category);

}
