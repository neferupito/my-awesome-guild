package io.neferupito.myawesomeguild.api.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MembershipDto implements Comparable<MembershipDto> {

    private Integer rank;
    private Long wowCharacterId;
    private Long guildId;

    @Override
    public int compareTo(MembershipDto m) {
        return this.rank.compareTo(m.rank);
    }

}
