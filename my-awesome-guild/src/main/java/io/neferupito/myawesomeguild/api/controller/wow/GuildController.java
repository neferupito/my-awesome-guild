package io.neferupito.myawesomeguild.api.controller.wow;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.service.wow.GuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wow")
public class GuildController {

    @Autowired
    private GuildService guildService;

    @GetMapping("/guild-import")
    public ResponseEntity<Object> importGuildFromBlizzard(
            @RequestParam
                    String region,
            @RequestParam
                    String slugRealm,
            @RequestParam
                    String name
    ) {
        try {
            return ResponseEntity.ok(guildService.importGuildFromBlizzard(region, slugRealm, name));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/guild/{guildId}")
    public ResponseEntity<Object> getGuildById(
            @PathVariable
                    Long guildId) {
        try {
            return ResponseEntity.ok(guildService.getGuildById(guildId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/guild/{guildId}/roster")
    public ResponseEntity<Object> getGuildRoster(
            @PathVariable
                    Long guildId) {
        try {
            return ResponseEntity.ok(guildService.getGuildRosterIds(guildId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/guild/{guildId}/guild-master")
    public ResponseEntity<Object> getGuildMaster(
            @PathVariable
                    Long guildId) {
        try {
            return ResponseEntity.ok(guildService.getGuildMaster(guildId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @PutMapping("/guild/{guildId}/roster/refresh")
    public ResponseEntity<Object> refreshGuildRoster(
            @PathVariable
                    Long guildId) {
        try {
            return ResponseEntity.ok(guildService.refreshGuildRoster(guildId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

}
