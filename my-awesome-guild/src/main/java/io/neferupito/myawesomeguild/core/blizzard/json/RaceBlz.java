package io.neferupito.myawesomeguild.core.blizzard.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaceBlz {

    private List<Rc> races;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Rc {

        private Integer id;
        private String name;
        private String side;

    }

}
