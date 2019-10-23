package io.neferupito.myawesomeguild.api.domain.wow;

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
