package io.neferupito.myawesomeguild.core.blizzard.json;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecializationBlz {

    @SerializedName("character_specializations")
    private List<Spec> characterSpecializations;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Spec {

        private Integer id;
        private String name;

    }

}
