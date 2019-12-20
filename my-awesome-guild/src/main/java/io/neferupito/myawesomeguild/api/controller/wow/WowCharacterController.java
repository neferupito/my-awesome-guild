package io.neferupito.myawesomeguild.api.controller.wow;


import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.service.wow.WowCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WowCharacterController {

    @Autowired
    private WowCharacterService wowCharacterService;

    @GetMapping("/wow-character-import")
    public ResponseEntity<Object> importCharacter(
            @RequestParam
                    String region,
            @RequestParam
                    String slugRealm,
            @RequestParam
                    String name
    ) {
        try {
            return ResponseEntity.ok(wowCharacterService.importNewWowCharacter(region, slugRealm, name));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/wow-character/{wowCharacterId}/link")
    public ResponseEntity<Object> linkWowCharacterToUser(
            @RequestParam
                    String userEmail,
            @PathVariable
                    Long wowCharacterId
    ) {
        try {
            wowCharacterService.linkWowCharacterToUser(wowCharacterId, userEmail);
            return ResponseEntity.ok().build();
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/wow-character/{wowCharacterId}")
    public ResponseEntity<Object> getWowCharacter(
            @PathVariable
                    Long wowCharacterId
    ) {
        try {
            return ResponseEntity.ok(wowCharacterService.findWowCharacterById(wowCharacterId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @PutMapping("/wow-character/{wowCharacterId}")
    public ResponseEntity<Object> refreshWowCharacter(
            @PathVariable
                    Long wowCharacterId
    ) {
        try {
            return ResponseEntity.ok(wowCharacterService.refreshWowCharacter(wowCharacterId));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @DeleteMapping("/wow-character/{wowCharacterId}")
    public ResponseEntity<Object> deleteWowCharacter(
            @PathVariable
                    Long wowCharacterId
    ) {
        try {
            wowCharacterService.deleteWowCharacter(wowCharacterId);
            return ResponseEntity.ok().build();
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/characters")
    public ResponseEntity<Object> findAllCharactersByUser(
            @RequestParam
                    String userEmail
    ) {
        try {
            return ResponseEntity.ok(wowCharacterService.findAllCharactersByUser(userEmail));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

}
