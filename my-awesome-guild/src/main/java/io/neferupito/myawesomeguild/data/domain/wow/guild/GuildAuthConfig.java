package io.neferupito.myawesomeguild.data.domain.wow.guild;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.neferupito.myawesomeguild.data.domain.wow.character.Character;
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
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "authConfig")
    @JsonIgnore
    private Guild guild;

    @OneToOne
    private Character guildMaster;

    @OneToMany
    private List<Character> officers;

}
