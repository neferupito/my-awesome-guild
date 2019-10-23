package io.neferupito.myawesomeguild.api.controller;

import io.neferupito.myawesomeguild.api.Response;
import io.neferupito.myawesomeguild.data.domain.wow.guild.Guild;
import io.neferupito.myawesomeguild.core.service.GuildService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/guild")
public class GuildController {

    @Autowired
    private GuildService guildService;

    @GetMapping("/{region}/{lang}/{server}/{slugName}")
    @ApiOperation(value = "Retrieve guild")
    public ResponseEntity<Response<Guild>> getGuildBySlugName(
            @PathVariable
            @ApiParam(value = "x", required = true)
                    String region,
            @PathVariable
            @ApiParam(value = "x", required = true)
                    String lang,
            @PathVariable
            @ApiParam(value = "x", required = true)
                    String server,
            @PathVariable
            @ApiParam(value = "x", required = true)
                    String slugName
    ) {
        Response<Guild> response = guildService.getGuildByName(region, lang, server, slugName);
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
