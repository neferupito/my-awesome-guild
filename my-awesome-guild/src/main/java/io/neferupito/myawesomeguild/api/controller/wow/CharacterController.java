package io.neferupito.myawesomeguild.api.controller.wow;


import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.service.wow.CharacterService;
import io.neferupito.myawesomeguild.core.service.wow.GuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wow")
public class CharacterController {

    @Autowired
    private CharacterService characterService;
    @Autowired
    private GuildService guildService;

    @GetMapping("/character-import")
    public ResponseEntity<Object> importCharacterFromBlizzard(
            @RequestParam
                    String region,
            @RequestParam
                    String slugRealm,
            @RequestParam
                    String name
    ) {
        try {
            return ResponseEntity.ok(characterService.importCharacterFromBlizzard(region, slugRealm, name));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/character/{wowCharacterId}/link")
    public ResponseEntity<Object> linkCharacterToUser(
            @RequestParam
                    String userEmail,
            @PathVariable
                    Long wowCharacterId
    ) {
        try {
            characterService.linkCharacterToUser(wowCharacterId, userEmail);
            return ResponseEntity.ok().build();
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/character/{wowCharacterId}/guild")
    public ResponseEntity<Object> getCharacterMembership(
            @PathVariable
                    Long wowCharacterId
    ) {
        try {
            return ResponseEntity.ok(guildService.getCharacterMembership(wowCharacterId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/character/{wowCharacterId}")
    public ResponseEntity<Object> getCharacter(
            @PathVariable
                    Long wowCharacterId
    ) {
        try {
            return ResponseEntity.ok(characterService.getCharacterById(wowCharacterId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @PutMapping("/character/{wowCharacterId}")
    public ResponseEntity<Object> refreshCharacter(
            @PathVariable
                    Long wowCharacterId
    ) {
        try {
            return ResponseEntity.ok(characterService.refreshCharacter(wowCharacterId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @DeleteMapping("/character/{wowCharacterId}/link")
    public ResponseEntity<Object> deleteCharacterLink(
            @PathVariable
                    Long wowCharacterId,
            @RequestParam
                    String userEmail
    ) {
        try {

            characterService.deleteCharacterLink(wowCharacterId, userEmail);
            return ResponseEntity.ok().build();
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/characters")
    public ResponseEntity<Object> getAllCharactersOfAnUser(
            @RequestParam
                    String userEmail
    ) {
        try {
            return ResponseEntity.ok(characterService.getAllCharactersOfAnUser(userEmail));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

}
