package io.neferupito.myawesomeguild.api.controller;

import io.neferupito.myawesomeguild.api.domain.api.Response;
import io.neferupito.myawesomeguild.api.domain.wow.Guild;
import io.neferupito.myawesomeguild.core.service.GuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuildController {

    @Autowired
    private GuildService guildService;

    @GetMapping("/guild/{region}/{lang}/{server}/{name}")
    public ResponseEntity<Response<Guild>> getGuildByName(
            @PathVariable String region,
            @PathVariable String lang,
            @PathVariable String server,
            @PathVariable String name
    ) {
        Response<Guild> response = guildService.getGuildByName(region, lang, server, name);
        if (response.isError()) {
            return ResponseEntity
                    .status(response.getErrorHttpStatus())
                    .build();
        } else {
            return ResponseEntity
                    .ok(response);
        }
    }

}
