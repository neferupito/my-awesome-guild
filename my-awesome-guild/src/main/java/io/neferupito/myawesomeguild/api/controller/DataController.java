package io.neferupito.myawesomeguild.api.controller;

import io.neferupito.myawesomeguild.api.domain.api.Response;
import io.neferupito.myawesomeguild.api.domain.wow.Faction;
import io.neferupito.myawesomeguild.api.domain.wow.Region;
import io.neferupito.myawesomeguild.data.entity.GuildEntity;
import io.neferupito.myawesomeguild.data.entity.LangEntity;
import io.neferupito.myawesomeguild.data.entity.ServerEntity;
import io.neferupito.myawesomeguild.data.repository.GuildRepository;
import io.neferupito.myawesomeguild.data.repository.LangRepository;
import io.neferupito.myawesomeguild.data.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    LangRepository langRepository;
    @Autowired
    ServerRepository serverRepository;
    @Autowired
    GuildRepository guildRepository;

    @GetMapping("/data/generate")
    public ResponseEntity<Response> generateData() {
        LangEntity fr = LangEntity.builder()
                .region(Region.EU)
                .slugName("fr")
                .build();
        fr = langRepository.save(fr);
        System.err.println(fr.toString());

        ServerEntity hyjal = ServerEntity.builder()
                .name("Hyjal")
                .slugName("hyjal")
                .lang(fr)
                .build();
        hyjal = serverRepository.save(hyjal);
        System.err.println(hyjal.toString());

        GuildEntity arkham = GuildEntity.builder()
                .faction(Faction.HORDE)
                .server(hyjal)
                .name("Arkham")
                .slugName("arkham")
                .build();
        GuildEntity sss = GuildEntity.builder()
                .faction(Faction.ALLIANCE)
                .server(hyjal)
                .name("Spooky Scary Skeletons")
                .slugName("spooky-scary-skeletons")
                .build();
        arkham = guildRepository.save(arkham);
        sss = guildRepository.save(sss);
        System.err.println(arkham.toString());
        System.err.println(sss.toString());
        return ResponseEntity.ok().build();
    }

}
