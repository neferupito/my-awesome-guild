package io.neferupito.myawesomeguild.api.controller.wow;


import io.neferupito.myawesomeguild.api.Response;
import io.neferupito.myawesomeguild.core.blizzard.client.CharacterBlizzardClient;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WowCharacterController {

    @Autowired
    private CharacterBlizzardClient blizzardClient;

    @GetMapping("/import/character")
    public ResponseEntity<Response<WowCharacter>> importCharacter(
            @RequestParam
                    String region,
            @RequestParam
                    String slugRealm,
            @RequestParam
                    String name
    ) {
        return ResponseEntity.ok(Response.<WowCharacter>builder().content(blizzardClient.importCharacter(region, slugRealm, name)).build());
    }

//    updateCharacterInfo
//    modifyMainSpec
//    claimMembership


}
