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
public class RaceBlz {

    private List<Rc> races;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Rc {

        private Map<String, String> name;
        private Integer id;

    }

}
