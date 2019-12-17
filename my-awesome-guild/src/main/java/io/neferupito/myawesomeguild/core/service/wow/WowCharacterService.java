package io.neferupito.myawesomeguild.core.service.wow;

import io.neferupito.myawesomeguild.api.Response;
import io.neferupito.myawesomeguild.core.blizzard.client.CharacterBlizzardClient;
import io.neferupito.myawesomeguild.core.blizzard.client.exception.BlizzardException;
import io.neferupito.myawesomeguild.data.domain.user.User;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowCharacter;
import io.neferupito.myawesomeguild.data.repository.user.UserRepository;
import io.neferupito.myawesomeguild.data.repository.wow.WowCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Thread.sleep;

@Service
public class WowCharacterService {

    @Autowired
    private WowCharacterRepository wowCharacterRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CharacterBlizzardClient blizzardClient;

    public Response<User> linkWowCharacterToUser(Long wowCharacterId, String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        WowCharacter wowCharacter = wowCharacterRepository.findById(wowCharacterId).get();
        if (wowCharacter.getUser() != null) {
            return Response.<User>builder()
                    .isError(true)
                    .errorHttpStatus(HttpStatus.FORBIDDEN)
                    .build();
        }
        wowCharacter.setUser(user);
        wowCharacter = wowCharacterRepository.save(wowCharacter);
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Response.<User>builder()
                .content(user)
                .build();
    }

    public List<WowCharacter> findAllCharactersByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail).get();
        return wowCharacterRepository.findByUser(user);
    }

    public WowCharacter refreshWowCharacter(Long wowCharacterId) {
        WowCharacter wowCharacter = wowCharacterRepository.findById(wowCharacterId).get();
        // TODO: handle null
        try {
            wowCharacter = blizzardClient.refreshCharacter(wowCharacter.getRegion().name(), wowCharacter.getRealm().getSlug(), wowCharacter.getName());
        } catch (BlizzardException e) {
            e.printStackTrace();
        }
        return wowCharacter;
    }

    public boolean deleteWowCharacter(Long wowCharacterId) {
        try {
            wowCharacterRepository.deleteById(wowCharacterId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
