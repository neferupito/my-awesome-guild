package io.neferupito.myawesomeguild.data.repository.user;

import io.neferupito.myawesomeguild.data.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
