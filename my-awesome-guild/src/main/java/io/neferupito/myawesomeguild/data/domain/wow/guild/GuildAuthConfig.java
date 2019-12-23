package io.neferupito.myawesomeguild.data.domain.wow.guild;

import io.neferupito.myawesomeguild.data.domain.wow.character.WowCharacter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuildAuthConfig {

    @Id
    private Long id;

    @OneToOne(mappedBy = "authConfig")
    private Guild guild;

    @OneToOne
    private WowCharacter guildMaster;

    @OneToMany
    private List<WowCharacter> officers;

}
