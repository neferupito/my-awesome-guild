package io.neferupito.myawesomeguild.core.transformer;

import io.neferupito.myawesomeguild.api.domain.wow.Guild;
import io.neferupito.myawesomeguild.data.entity.GuildEntity;
import org.springframework.stereotype.Service;

@Service
public class GuildTransformer {

    public Guild transformFromEntity(GuildEntity entity) {
        return Guild.builder()
                .id(entity.getId())
                .server(entity.getServer())
                .name(entity.getName())
                .slugName(entity.getSlugName())
                .faction(entity.getFaction())
                .build();
    }

    public GuildEntity transformToEntity(Guild guild) {
        return GuildEntity.builder()
                .id(guild.getId())
                .server(guild.getServer())
                .name(guild.getName())
                .slugName(guild.getSlugName())
                .faction(guild.getFaction())
                .build();
    }

}
