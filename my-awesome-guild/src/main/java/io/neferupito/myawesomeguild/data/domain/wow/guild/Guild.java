package io.neferupito.myawesomeguild.api.domain.wow.guild;

import io.neferupito.myawesomeguild.api.domain.wow.server.Faction;
import io.neferupito.myawesomeguild.data.entity.ServerEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Guild {

    private Long id;
    private String name;
    private String slugName;
    private Faction faction;
    private ServerEntity server;

}
