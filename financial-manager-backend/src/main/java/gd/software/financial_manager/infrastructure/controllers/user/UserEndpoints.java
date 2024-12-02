package gd.software.financial_manager.infrastructure.controllers.user;

import gd.software.financial_manager.infrastructure.dtos.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(UserEndpoints.class);

    @PostMapping
    public ResponseEntity<UserData> save(@RequestBody UserData userData) {
        return null;
    }
}
