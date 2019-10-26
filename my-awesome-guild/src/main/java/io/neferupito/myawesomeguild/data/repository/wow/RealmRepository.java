package io.neferupito.myawesomeguild.data.repository.wow;

import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import io.neferupito.myawesomeguild.data.domain.wow.server.Region;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RealmRepository extends CrudRepository<Realm, Integer> {

    List<Realm> findByRegion(Region region);

}
