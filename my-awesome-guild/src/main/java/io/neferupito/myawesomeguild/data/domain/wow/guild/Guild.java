package io.neferupito.myawesomeguild.data.domain.wow.guild;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.neferupito.myawesomeguild.data.domain.wow.server.Faction;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
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
public class Guild {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Realm realm;

    private String name;

    private String slugName;

    private Faction faction;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", locale = "fr_FR", timezone = "Europe/Paris")
    private Date lastUpdate;

}
