package io.neferupito.myawesomeguild.api.controller.wow;


import io.neferupito.myawesomeguild.api.Response;
import io.neferupito.myawesomeguild.core.blizzard.client.CharacterBlizzardClient;
import io.neferupito.myawesomeguild.core.blizzard.client.exception.BlizzardException;
import io.neferupito.myawesomeguild.core.service.wow.WowCharacterService;
import io.neferupito.myawesomeguild.data.domain.user.User;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WowCharacterController {

    @Autowired
    private CharacterBlizzardClient blizzardClient;

    @Autowired
    private WowCharacterService wowCharacterService;

    @GetMapping("/find-character")
    public ResponseEntity<Response<WowCharacter>> importCharacter(
            @RequestParam
                    String region,
            @RequestParam
                    String slugRealm,
            @RequestParam
                    String name
    ) {
        try {
            WowCharacter character = blizzardClient.importNewCharacter(region, slugRealm, name);
            return ResponseEntity.ok(Response.<WowCharacter>builder().content(character).build());
        } catch (BlizzardException e) {
            return ResponseEntity.ok(Response.<WowCharacter>builder()
                    .isError(true)
                    .externalHttpStatus(e.getStatus())
                    .externalHttpMessage(e.getErrorMessage())
                    .build());
        }
    }

    @GetMapping("/link-character")
    public ResponseEntity<Response<User>> linkWowCharacterToUser(
            @RequestParam
                    String userEmail,
            @RequestParam
                    Long wowCharacterId
    ) {
        Response<User> response = wowCharacterService.linkWowCharacterToUser(wowCharacterId, userEmail);
        if (response.isError()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refresh-character")
    public ResponseEntity<WowCharacter> refreshWowCharacter(
            @RequestParam
                    Long wowCharacterId
    ) {
        WowCharacter wowCharacter = wowCharacterService.refreshWowCharacter(wowCharacterId);
        return ResponseEntity.ok(wowCharacter);
    }

    @GetMapping("/delete-character")
    public ResponseEntity deleteWowCharacter(
            @RequestParam
                    Long wowCharacterId
    ) {
        boolean isOk = wowCharacterService.deleteWowCharacter(wowCharacterId);
        if (isOk) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/characters")
    public ResponseEntity<List<WowCharacter>> findAllCharactersByUser(
            @RequestParam
                    String userEmail
    ) {
        return ResponseEntity.ok(wowCharacterService.findAllCharactersByUser(userEmail));
    }

//    //TODO: to remove
//    @GetMapping("/characters")
//    public ResponseEntity<List<WowCharacter>> findAllCharacters() {
//        return ResponseEntity.ok(wowCharacterService.findAllImportedCharacters());
//    }

//    updateCharacterInfo
//    modifyMainSpec
//    claimMembership


}
