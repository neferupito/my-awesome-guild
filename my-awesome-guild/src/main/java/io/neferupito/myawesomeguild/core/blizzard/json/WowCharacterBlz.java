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
        private Key key;
        private Long id;
        private String name;
        private RealmBlz.Rlm realm;
    }

    //{
//        "_links": {
//        "self": {
//            "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0?namespace=profile-eu"
//        }
//    },
//        "id": 179670840,
//            "name": "Hàrà",
//            "gender": {
//        "type": "FEMALE",
//                "name": "Femme"
//    },
//        "faction": {
//        "type": "ALLIANCE",
//                "name": "Alliance"
//    },
//        "race": {
//        "key": {
//            "href": "https://eu.api.blizzard.com/data/wow/playable-race/29?namespace=static-8.2.5_31884-eu"
//        },
//        "name": "Elfe du Vide",
//                "id": 29
//    },
//        "character_class": {
//        "key": {
//            "href": "https://eu.api.blizzard.com/data/wow/playable-class/10?namespace=static-8.2.5_31884-eu"
//        },
//        "name": "Moine",
//                "id": 10
//    },
//        "active_spec": {
//        "key": {
//            "href": "https://eu.api.blizzard.com/data/wow/playable-specialization/270?namespace=static-8.2.5_31884-eu"
//        },
//        "name": "Tisse-brume",
//                "id": 270
//    },
//        "realm": {
//        "key": {
//            "href": "https://eu.api.blizzard.com/data/wow/realm/542?namespace=dynamic-eu"
//        },
//        "name": "Hyjal",
//                "id": 542,
//                "slug": "hyjal"
//    },
//        "guild": {
//        "key": {
//            "href": "https://eu.api.blizzard.com/data/wow/guild/hyjal/spooky-scary-skeletons?namespace=profile-eu"
//        },
//        "name": "Spooky Scary Skeletons",
//                "id": 82052965,
//                "realm": {
//            "key": {
//                "href": "https://eu.api.blizzard.com/data/wow/realm/542?namespace=dynamic-eu"
//            },
//            "name": "Hyjal",
//                    "id": 542,
//                    "slug": "hyjal"
//        }
//    },
//        "level": 120,
//            "experience": 0,
//            "achievement_points": 15790,
//            "achievements": {
//        "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0/achievements?namespace=profile-eu"
//    },
//        "titles": {
//        "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0/titles?namespace=profile-eu"
//    },
//        "pvp_summary": {
//        "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0/pvp-summary?namespace=profile-eu"
//    },
//        "raid_progression": {
//        "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0/raid-progression?namespace=profile-eu"
//    },
//        "media": {
//        "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0/character-media?namespace=profile-eu"
//    },
//        "last_login_timestamp": 1576499433000,
//            "average_item_level": 411,
//            "equipped_item_level": 409,
//            "specializations": {
//        "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0/specializations?namespace=profile-eu"
//    },
//        "statistics": {
//        "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0/statistics?namespace=profile-eu"
//    },
//        "mythic_keystone_profile": {
//        "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0/mythic-keystone-profile?namespace=profile-eu"
//    },
//        "equipment": {
//        "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0/equipment?namespace=profile-eu"
//    },
//        "appearance": {
//        "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0/appearance?namespace=profile-eu"
//    },
//        "collections": {
//        "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0/collections?namespace=profile-eu"
//    },
//        "active_title": {
//        "key": {
//            "href": "https://eu.api.blizzard.com/data/wow/title/240?namespace=static-8.2.5_31884-eu"
//        },
//        "name": "la Vieille dame aux chats",
//                "id": 240,
//                "display_string": "{name}, la Vieille dame aux chats"
//    },
//        "reputations": {
//        "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0/reputations?namespace=profile-eu"
//    },
//        "quests": {
//        "href": "https://eu.api.blizzard.com/profile/wow/character/hyjal/h%C3%A0r%C3%A0/quests?namespace=profile-eu"
//    }
//    }

}
