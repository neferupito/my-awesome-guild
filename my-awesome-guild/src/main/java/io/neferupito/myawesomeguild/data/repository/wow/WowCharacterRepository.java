package io.neferupito.myawesomeguild.data.repository.wow;

import io.neferupito.myawesomeguild.data.domain.wow.character.WowCharacter;
import org.springframework.data.repository.CrudRepository;

public interface WowCharacterRepository extends CrudRepository<WowCharacter, Long> {
}
