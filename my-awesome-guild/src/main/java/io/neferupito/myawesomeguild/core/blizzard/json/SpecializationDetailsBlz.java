package io.neferupito.myawesomeguild.core.blizzard.json;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecializationDetailsBlz {

    @SerializedName("playable_class")
    private Cl wowClass;
    private Rol role;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Cl {

        private Integer id;
        private String name;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Rol {

        private String type;
        private String name;

    }


}
