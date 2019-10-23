package io.neferupito.myawesomeguild.data.domain.blizzard;

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
