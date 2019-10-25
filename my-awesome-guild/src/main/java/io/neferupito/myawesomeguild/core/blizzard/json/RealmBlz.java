package io.neferupito.myawesomeguild.core.blizzard.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealmBlz {

    private List<Rlm> realms;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Rlm {

        private Integer id;
        private String name;
        private String slug;

    }

}
