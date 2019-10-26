package io.neferupito.myawesomeguild.core.blizzard.json;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WowCharacterBlz {

    private Long id;
    private String name;
    private Gndr gender;
    private Fct faction;
    private RaceBlz.Rc race;
    @SerializedName("character_class")
    private SpecializationDetailsBlz.Cl characterClass;
    @SerializedName("active_spec")
    private SpecializationBlz.Spec activeSpec;
    private RealmBlz.Rlm realm;
    private Gld guild;
    private Integer level;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Fct {
        private String type;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Gndr {
        private String type;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Gld {
        private Long id;
        private String name;
        private RealmBlz.Rlm realm;
    }

}
