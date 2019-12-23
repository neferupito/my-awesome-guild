package io.neferupito.myawesomeguild.core.blizzard.json;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuildBlz {

    private Long id;
    private String name;
    private WowCharacterBlz.Fct faction;
    private RealmBlz.Rlm realm;
    private Key roster;
    @SerializedName("created_timestamp")
    private Long created;


//    {
//        "_links":{  },
//        "id":69463965,
//        "name":"Arkham",
//        "faction":{  },
//        "achievement_points":1100,
//        "member_count":125,
//        "realm":{  },
//        "crest":{  },
//        "roster":{"href":"https://eu.api.blizzard.com/data/wow/guild/hyjal/arkham/roster?namespace=profile-eu"},
//        "achievements":{  },
//        "created_timestamp":1526507994000
//    }

}
