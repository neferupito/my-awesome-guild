package io.neferupito.myawesomeguild.core.blizzard.json;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenBlz {

    @SerializedName("access_token")
    private String token;

}
