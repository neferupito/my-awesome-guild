package io.neferupito.myawesomeguild.api.controller;

import io.neferupito.myawesomeguild.core.blizzard.client.ConstantDataImportBlizzardClient;
import io.neferupito.myawesomeguild.data.domain.user.User;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.domain.wow.server.Region;
import io.neferupito.myawesomeguild.data.repository.user.UserRepository;
import io.neferupito.myawesomeguild.data.repository.wow.RealmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class DataController {

    @Autowired
    private ConstantDataImportBlizzardClient client;

    @Autowired
    RealmRepository realmRepository;
    @Autowired
    UserRepository userRepository;

//    @PostConstruct
//    public void init() {
//        generate();
//    }

    @GetMapping("/data/gen")
    public ResponseEntity<Object> generate() {
        List<Realm> r = realmRepository.findByRegion(Region.EU);
        if (r == null || r.isEmpty()) {
            client.importAllData();
            User user1 = User.builder()
                    .email("nefee")
                    .build();
            user1 = userRepository.save(user1);
            User user2 = User.builder()
                    .email("kirri")
                    .build();
            user2 = userRepository.save(user2);
        }
        return ResponseEntity.ok().build();
    }

}
