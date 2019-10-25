package io.neferupito.myawesomeguild.data.domain.wow.character;

import io.neferupito.myawesomeguild.data.domain.wow.server.Faction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Race {

    @Id
    private Integer id;
    private String name;
    private Faction faction;

}
