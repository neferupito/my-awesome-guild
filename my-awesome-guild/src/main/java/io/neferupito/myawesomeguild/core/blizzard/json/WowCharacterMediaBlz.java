package io.neferupito.myawesomeguild.core.blizzard.json;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WowCharacterMediaBlz {

    @SerializedName("avatar_url")
    private String avatarURL;
    @SerializedName("bust_url")
    private String bustURL;
    @SerializedName("render_url")
    private String renderURL;

}
