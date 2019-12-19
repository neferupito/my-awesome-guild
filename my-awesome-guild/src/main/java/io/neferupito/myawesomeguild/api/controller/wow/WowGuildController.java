package io.neferupito.myawesomeguild.api.controller.wow;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.service.wow.WowGuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WowGuildController {

    @Autowired
    private WowGuildService wowGuildService;

    @GetMapping("/wow-guild-import")
    public ResponseEntity<Object> importNewGuild(
            @RequestParam
                    String region,
            @RequestParam
                    String slugRealm,
            @RequestParam
                    String name
    ) {
        try {
            return ResponseEntity.ok(wowGuildService.importNewWowGuild(region, slugRealm, name));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/wow-guild/{guildId}/roster")
    public ResponseEntity<Object> getRoster(Long guildId) {
        try {
            return ResponseEntity.ok(wowGuildService.findAllMembersFromGuild(guildId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

}
