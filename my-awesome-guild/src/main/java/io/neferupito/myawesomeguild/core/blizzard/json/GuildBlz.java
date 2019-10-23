package io.neferupito.myawesomeguild.data.domain.blizzard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuildBlz {

    private Long lastModified;
    private String name;
    private String realm;
    private String battlegroup;
    private List<MembershipBlz> members;

}
