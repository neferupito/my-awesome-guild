package io.neferupito.myawesomeguild.data.repository.wow;

import io.neferupito.myawesomeguild.data.domain.wow.server.Realm;
import org.springframework.data.repository.CrudRepository;

public interface RealmRepository extends CrudRepository<Realm, Long> {
}
