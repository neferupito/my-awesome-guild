package io.neferupito.myawesomeguild.core.blizzard.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RealmBlz {

    private List<Rlm> realms;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class Rlm {

        private Map<String, String> name;
        private String slug;

    }

}
