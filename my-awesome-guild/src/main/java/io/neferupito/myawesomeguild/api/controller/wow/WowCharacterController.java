package io.neferupito.myawesomeguild.api.controller.wow.character;

import io.neferupito.myawesomeguild.api.Response;
import io.neferupito.myawesomeguild.data.domain.user.User;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowCharacter;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public class WowCharacterController {

    public ResponseEntity<Response<WowCharacter>> createCharacter(WowCharacter wowCharacter) {
        return ResponseEntity.ok(Response.<WowCharacter>builder().content(WowCharacter.builder().build()).build());
    }

    public ResponseEntity<Response<WowCharacter>> readCharacter(Long id) {
        return ResponseEntity.ok(Response.<WowCharacter>builder().content(WowCharacter.builder().build()).build());
    }

    public ResponseEntity<Response<List<WowCharacter>>> readAllCharactersFromUser(User user) {
        return ResponseEntity.ok(Response.<List<WowCharacter>>builder().content(Arrays.asList(WowCharacter.builder().build())).build());
    }
    
    public ResponseEntity<Response<WowCharacter>> updateCharacter(WowCharacter wowCharacter) {
        return ResponseEntity.ok(Response.<WowCharacter>builder().content(WowCharacter.builder().build()).build());
    }

    public ResponseEntity<Response<WowCharacter>> deleteCharacter(WowCharacter wowCharacter) {
        return ResponseEntity.ok(Response.<WowCharacter>builder().content(WowCharacter.builder().build()).build());
    }

}
