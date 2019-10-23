package io.neferupito.myawesomeguild.data.repository.wow;

import io.neferupito.myawesomeguild.data.domain.wow.server.Server;
import org.springframework.data.repository.CrudRepository;

public interface ServerRepository extends CrudRepository<Server, Long> {
}
