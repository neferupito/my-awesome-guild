package io.neferupito.myawesomeguild.data.repository.wow;

import io.neferupito.myawesomeguild.data.domain.wow.server.Battlegroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BattlegroupRepository extends CrudRepository<Battlegroup, Long> {

    List<Battlegroup> findAllBySlug(String slug);

}
