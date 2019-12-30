package io.neferupito.myawesomeguild.api.controller.wow;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.service.wow.GuildAuthConfigService;
import io.neferupito.myawesomeguild.data.domain.wow.guild.GuildAuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wow")
public class GuildAuthConfigController {

    @Autowired
    private GuildAuthConfigService guildAuthConfigService;

    @GetMapping("/guild/{guildId}/auth-config")
    public ResponseEntity<Object> getAuthConfig(
            @PathVariable
                    Long guildId
    ) {
        try {
            return ResponseEntity.ok(guildAuthConfigService.getGuildAuthConfig(guildId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @PutMapping("/guild/{guildId}/auth-config")
    public ResponseEntity<Object> modifyAuthConfig(
            @PathVariable
                    Long guildId,
            @RequestBody
                    GuildAuthConfig guildAuthConfig
    ) {
        try {
            return ResponseEntity.ok(guildAuthConfigService.modifyGuildAuthConfig(guildId, guildAuthConfig));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

}
