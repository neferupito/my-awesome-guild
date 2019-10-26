package io.neferupito.myawesomeguild.core.service.user;

import io.neferupito.myawesomeguild.api.Response;
import io.neferupito.myawesomeguild.data.domain.user.User;
import io.neferupito.myawesomeguild.data.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Response<User> createNewUser(User user) {
        Response response;
        try {
            response = Response.<User>builder()
                    .content(userRepository.save(user))
                    .isError(false)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.builder()
                    .isError(true)
                    .errorHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
        return response;
    }

    public Response<Iterable<User>> findAllUsers() {
        Response response;
        try {
            response = Response.<Iterable<User>>builder()
                    .content(userRepository.findAll())
                    .isError(false)
                    .build();
        } catch (Exception e) {
//            DataIntegrityViolationException
            e.printStackTrace();
            response = Response.builder()
                    .isError(true)
                    .errorHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
        return response;
    }

}
