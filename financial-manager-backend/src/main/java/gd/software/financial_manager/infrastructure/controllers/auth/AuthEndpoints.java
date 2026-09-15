package gd.software.financial_manager.infrastructure.controllers.auth;

import gd.software.financial_manager.infrastructure.dtos.AuthResponse;
import gd.software.financial_manager.infrastructure.dtos.GoogleLoginRequest;
import gd.software.financial_manager.infrastructure.dtos.LoginRequest;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import gd.software.financial_manager.infrastructure.persistence.repository.UserRepository;
import gd.software.financial_manager.infrastructure.utils.GoogleTokenValidator;
import gd.software.financial_manager.infrastructure.utils.JwtUtil;
import gd.software.financial_manager.infrastructure.utils.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthEndpoints {

    private static final Logger logger = LoggerFactory.getLogger(AuthEndpoints.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoogleTokenValidator googleTokenValidator;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        logger.info("Login attempt for email {}.", request.email());

        UserRow user = userRepository.findByEmail(request.email()).orElse(null);

        if (user == null || user.getPassword() == null
                || !PasswordUtil.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = JwtUtil.generateToken(user.getId().toString(), user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, user.getId().toString(), user.getEmail()));
    }

    @PostMapping("/google")
    public ResponseEntity<AuthResponse> googleLogin(@RequestBody GoogleLoginRequest request) {
        logger.info("Google login attempt.");

        GoogleTokenValidator.GoogleUserInfo userInfo = googleTokenValidator.validate(request.accessToken());
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<UserRow> existingByProvider = userRepository
                .findByProviderAndProviderId("google", userInfo.sub());

        UserRow user;
        if (existingByProvider.isPresent()) {
            user = existingByProvider.get();
        } else {
            Optional<UserRow> existingByEmail = userRepository.findByEmail(userInfo.email());
            if (existingByEmail.isPresent()) {
                user = existingByEmail.get();
                user.setProvider("google");
                user.setProviderId(userInfo.sub());
                user = userRepository.save(user);
            } else {
                user = userRepository.save(UserRow.builder()
                        .email(userInfo.email())
                        .provider("google")
                        .providerId(userInfo.sub())
                        .build());
            }
        }

        String token = JwtUtil.generateToken(user.getId().toString(), user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, user.getId().toString(), user.getEmail()));
    }
}
