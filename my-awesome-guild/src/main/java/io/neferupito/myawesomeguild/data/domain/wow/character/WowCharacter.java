package io.neferupito.myawesomeguild.data.domain.wow.character;

import io.neferupito.myawesomeguild.data.domain.user.User;
import io.neferupito.myawesomeguild.data.domain.wow.guild.Membership;
import io.neferupito.myawesomeguild.data.domain.wow.server.Faction;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.domain.wow.server.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WowCharacter {

    @Id
    private Long id;
    private String name;
    @ManyToOne
    private Race race;
    private Integer level;
    private Region region;
    @ManyToOne
    private Realm realm;
    private Faction faction;
    @ManyToOne
    private WowClass wowClass;
    @ManyToOne
    private Specialization mainSpec;
    @OneToOne
    private Membership membership;
    @ManyToOne
    @JoinColumn()
    private User user;

}
