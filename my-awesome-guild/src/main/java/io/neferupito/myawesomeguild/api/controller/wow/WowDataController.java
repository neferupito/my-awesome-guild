package io.neferupito.myawesomeguild.api.controller.wow;

import io.neferupito.myawesomeguild.api.Response;
import io.neferupito.myawesomeguild.core.service.wow.WowDataService;
import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.domain.wow.server.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WowDataController {

    @Autowired
    private WowDataService wowDataService;

    @GetMapping("/realms")
    public ResponseEntity<Response<List<Realm>>> getAllRealms() {
        return ResponseEntity.ok(wowDataService.getAllRealms(Region.EU));
    }

}