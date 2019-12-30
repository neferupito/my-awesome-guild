package io.neferupito.myawesomeguild.data.domain.wow.character;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.Date;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Character {

    @Id
    private Long id;
    private String name;
    @ManyToOne
    private Race race;
    private Integer level;
    @Enumerated(EnumType.STRING)
    private Region region;
    @ManyToOne
    private Realm realm;
    @Enumerated(EnumType.STRING)
    private Faction faction;
    @ManyToOne
    private WowClass wowClass;
    @ManyToOne
    private Specialization mainSpec;
    @ManyToOne
//    @JoinColumn
    private User user;
    private boolean isClaimed;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", locale = "fr_FR", timezone = "Europe/Paris")
    private Date lastUpdate;
    private String avatarUrl;

}
