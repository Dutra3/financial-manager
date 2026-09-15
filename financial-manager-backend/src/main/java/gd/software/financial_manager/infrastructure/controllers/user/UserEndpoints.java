package gd.software.financial_manager.infrastructure.controllers.user;

import gd.software.financial_manager.infrastructure.dtos.UserData;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import gd.software.financial_manager.infrastructure.persistence.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(UserEndpoints.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<UserData> save(@RequestBody UserData userData) {
        logger.info("Creating user with email {}.", userData.email());

        if (userRepository.findByEmail(userData.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        UserRow saved = userRepository.save(UserRow.builder()
                .email(userData.email())
                .password(userData.password())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UserData(saved.getId(), saved.getEmail(), "")
        );
    }
}
