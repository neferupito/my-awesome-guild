package io.neferupito.myawesomeguild.core.blizzard.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RosterBlz {

    private List<Mmb> members;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Mmb {
        private Chr character;
        private Integer rank;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Chr {
        private Long id;
        private String name;
        private RealmBlz.Rlm realm;

    }

}
