package io.neferupito.myawesomeguild.api.controller.wow;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.service.wow.WowGuildService;
import io.neferupito.myawesomeguild.data.domain.wow.guild.GuildAuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/wow-guild/{guildId}")
    public ResponseEntity<Object> getGuildById(
            @PathVariable
                    Long guildId) {
        try {
            return ResponseEntity.ok(wowGuildService.findGuildById(guildId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/wow-guild/{guildId}/roster")
    public ResponseEntity<Object> getRoster(
            @PathVariable
                    Long guildId) {
        try {
            return ResponseEntity.ok(wowGuildService.findAllMembersFromGuild(guildId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/wow-guild/{guildId}/guild-master")
    public ResponseEntity<Object> getGuildMaster(
            @PathVariable
                    Long guildId) {
        try {
            return ResponseEntity.ok(wowGuildService.getGuildMaster(guildId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @PutMapping("/wow-guild/{guildId}/roster/refresh")
    public ResponseEntity<Object> refreshRoster(
            @PathVariable
                    Long guildId) {
        try {
            return ResponseEntity.ok(wowGuildService.refreshRoster(guildId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @PostMapping("/wow-guild/{guildId}/auth-config")
    public ResponseEntity<Object> addAuthConfig(
            @RequestBody
                    GuildAuthConfig guildAuthConfig,
            @PathVariable
                    Long guildId
    ) {
        try {
            wowGuildService.addGuildAuthConfig(guildId, guildAuthConfig);
            return ResponseEntity.ok().build();
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/wow-guild/{guildId}/auth-config")
    public ResponseEntity<Object> addAuthConfig(
            @PathVariable
                    Long guildId
    ) {
        try {
            return ResponseEntity.ok(wowGuildService.getGuildAuthConfig(guildId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

}