package io.neferupito.myawesomeguild.core.service.user;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.data.domain.user.User;
import io.neferupito.myawesomeguild.data.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createNewUser(User user) throws AwesomeException {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new AwesomeException(HttpStatus.BAD_REQUEST, "Erreur lors de l'enregistrement du nouvel utilisateur " + e.getClass());
        }
    }

    public Iterable<User> findAllUsers() throws AwesomeException {
        try {
            return userRepository.findAll();

        } catch (Exception e) {
            throw new AwesomeException(HttpStatus.BAD_REQUEST, "Erreur lors de l'appel de tous les utilisateurs " + e.getClass());
        }
    }

}
