package io.neferupito.myawesomeguild.data.repository.wow;

import io.neferupito.myawesomeguild.data.domain.wow.character.Race;
import io.neferupito.myawesomeguild.data.domain.wow.character.WowClass;
import org.springframework.data.repository.CrudRepository;

public interface WowClassRepository extends CrudRepository<WowClass, Integer> {

}
