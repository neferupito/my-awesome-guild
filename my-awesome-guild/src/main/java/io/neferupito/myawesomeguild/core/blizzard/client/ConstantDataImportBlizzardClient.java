package io.neferupito.myawesomeguild.core.blizzard.client;

import com.google.gson.Gson;
import io.neferupito.myawesomeguild.core.blizzard.json.RealmBlz;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.domain.wow.server.Region;
import io.neferupito.myawesomeguild.data.repository.wow.RealmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Slf4j
public class ConstantDataImportBlizzardClient extends BlizzardClient {

    //    @Autowired
//    private BattlegroupRepository battlegroupRepository;
    @Autowired
    private RealmRepository realmRepository;

    public void importAllData() {
        for (Region region :
                Region.values()) {
//            importBattlegroups(region);
            importRealms(region);
        }
    }

//    private void importBattlegroups(Region region) {
//        try {
//            String path = "/wow/data/battlegroups/";
//            URI uri = new URI(
//                    getScheme(),
//                    region.toString().toLowerCase() + getAuthority(),
//                    path,
//                    getTokenQuery(),
//                    null);
//            String response = invokeBlizzardApi(uri);
//            BattlegroupBlz battlegroupBlz = new Gson().fromJson(response, BattlegroupBlz.class);
//            for (BattlegroupBlz.Bg bg :
//                    battlegroupBlz.getBattlegroups()) {
//                battlegroupRepository.save(Battlegroup.builder()
//                        .region(region)
//                        .name(bg.getName())
//                        .slug(bg.getSlug())
//                        .build());
//            }
//        } catch (URISyntaxException e) {
//            log.error("dsqkhfdlqksd", e);
//        }
//    }

    private void importRealms(Region region) {
        try {
            String path = "/data/wow/realm/index";
            URI uri = new URI(
                    getScheme(),
                    region.toString().toLowerCase() + getAuthority(),
                    path,
                    "namespace=dynamic-" + region.toString().toLowerCase() + "&" + getTokenQuery(),
                    null);
            String response = invokeBlizzardApi(uri);
            RealmBlz realmBlz = new Gson().fromJson(response, RealmBlz.class);
            for (RealmBlz.Rlm rlm :
                    realmBlz.getRealms()) {
                realmRepository.save(Realm.builder()
                        .region(region)
                        .name(rlm.getName().get("fr_FR"))
                        .slug(rlm.getSlug())
                        .build());
            }
        } catch (URISyntaxException e) {
            log.error("dsqkhfdlqksd", e);
        }
    }

}
