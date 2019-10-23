package io.neferupito.myawesomeguild.data.repository.recruitment;

import io.neferupito.myawesomeguild.data.domain.recruitment.Apply;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ApplyRepository extends CrudRepository<Apply, String> {
}
