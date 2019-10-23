package io.neferupito.myawesomeguild.data.repository;

import io.neferupito.myawesomeguild.data.entity.ServerEntity;
import org.springframework.data.repository.CrudRepository;

public interface ServerRepository extends CrudRepository<ServerEntity, Long> {
}
