package io.neferupito.myawesomeguild.data.entity;

import io.neferupito.myawesomeguild.api.domain.wow.Faction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="guilds")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuildEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String slugName;
    private Faction faction;
    @ManyToOne
    private ServerEntity server;

}
