package io.neferupito.myawesomeguild.api.controller;

import io.neferupito.myawesomeguild.core.blizzard.client.ConstantDataImportBlizzardClient;
import io.neferupito.myawesomeguild.data.domain.wow.server.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    private ConstantDataImportBlizzardClient client;

    @GetMapping("/data/gen")
    public ResponseEntity generate() {
        client.importBattlegroups(Region.EU, "fr_FR");
        return ResponseEntity.ok().build();
    }

}
