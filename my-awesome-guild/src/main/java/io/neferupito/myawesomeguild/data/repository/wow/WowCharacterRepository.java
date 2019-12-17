package io.neferupito.myawesomeguild.data.repository.wow;

import io.neferupito.myawesomeguild.data.domain.user.User;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowCharacter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WowCharacterRepository extends CrudRepository<WowCharacter, Long> {

    List<WowCharacter> findByUser(User user);

}
