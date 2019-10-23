package io.neferupito.myawesomeguild.core.blizzard.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipBlz {

    private WowCharacterBlz character;
    private Integer rank;

}
