package io.neferupito.myawesomeguild.data.repository;

import io.neferupito.myawesomeguild.data.entity.GuildEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GuildRepository extends CrudRepository<GuildEntity, Long> {

//    @Query("SELECT a FROM Article a WHERE a.title=:title and a.category=:category")
//    List<GuildEntity> findGuildBy(@Param("region") String region, @Param("category") String category);

    List<GuildEntity> findBySlugName(String slugName);

}
