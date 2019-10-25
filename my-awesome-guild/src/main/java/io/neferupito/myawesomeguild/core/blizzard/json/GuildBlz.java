package io.neferupito.myawesomeguild.core.blizzard.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuildBlz {

    private Long lastModified;
    private String name;
    private String realm;
    private String battlegroup;
    private List<MembershipBlz> members;

}
