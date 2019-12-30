package io.neferupito.myawesomeguild.api.controller.user;

import io.neferupito.myawesomeguild.api.controller.AwesomeException;
import io.neferupito.myawesomeguild.core.service.user.UserService;
import io.neferupito.myawesomeguild.data.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
//@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Object> createNewUser(
//            @Valid
            @RequestBody
                    User user) {
        try {
            return ResponseEntity.ok(userService.addNewUser(user));
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Object> findAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (AwesomeException e) {
            return e.buildResponseEntity();
        }
    }

}
