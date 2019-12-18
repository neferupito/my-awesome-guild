package io.neferupito.myawesomeguild.data.domain.wow.guild;

import io.neferupito.myawesomeguild.data.domain.wow.character.WowCharacter;
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
public class Membership {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn
    private WowCharacter wowCharacter;

    @ManyToOne
    @JoinColumn
    private Guild guild;

    private Integer rank;

}
