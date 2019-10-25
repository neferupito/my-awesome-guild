package io.neferupito.myawesomeguild.core.blizzard.json;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WowCharacterBlz {

    private String name;
    private String realm;
    @SerializedName("class")
    private Integer clazz;
    private Integer race;
    private Integer gender;
    private Integer level;

}
