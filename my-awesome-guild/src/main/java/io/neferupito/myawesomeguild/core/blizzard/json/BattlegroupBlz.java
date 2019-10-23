package io.neferupito.myawesomeguild.core.blizzard.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BattlegroupBlz {

    private List<Bg> battlegroups;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Bg {
        private String name;
        private String slug;
    }

}
