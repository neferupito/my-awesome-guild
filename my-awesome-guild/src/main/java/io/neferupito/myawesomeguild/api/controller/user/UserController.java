package io.neferupito.myawesomeguild.api.controller.user;

import io.neferupito.myawesomeguild.api.Response;
import io.neferupito.myawesomeguild.core.service.user.UserService;
import io.neferupito.myawesomeguild.data.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
//@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Response<User>> createNewUser(
//            @Valid
            @RequestBody
                    User user) {
        System.err.println("USER RECEIVED\n"+user.toString());
        Response response = userService.createNewUser(user);
        if (response.isError()) {
            return ResponseEntity.status(response.getErrorHttpStatus()).build();
        }
        return ResponseEntity.ok(response);

    }

    @GetMapping("/users")
    public ResponseEntity<Response<Iterable<User>>> findAllUsers() {
        Response response = userService.findAllUsers();
        if (response.isError()) {
            return ResponseEntity.status(response.getErrorHttpStatus()).build();
        }
        return ResponseEntity.ok(response);

    }

}
