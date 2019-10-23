package io.neferupito.myawesomeguild.data.domain.blizzard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
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
