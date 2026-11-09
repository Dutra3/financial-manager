package gd.software.financial_manager.domain.usecase.auth;

import gd.software.financial_manager.infrastructure.persistence.relational.PasswordResetTokenRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import gd.software.financial_manager.infrastructure.persistence.repository.PasswordResetTokenRepository;
import gd.software.financial_manager.infrastructure.persistence.repository.UserRepository;
import gd.software.financial_manager.infrastructure.utils.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HexFormat;

@Service
public class PasswordResetService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetService.class);
    private static final int EXPIRY_MINUTES = 30;

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public PasswordResetService(PasswordResetTokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void requestReset(String email) {
        // Always silent: never reveal whether the email exists.
        userRepository.findByEmail(email).ifPresent(user -> {
            String token = generateToken();
            PasswordResetTokenRow row = PasswordResetTokenRow.builder()
                    .user(user)
                    .tokenHash(hash(token))
                    .expiresAt(LocalDateTime.now().plusMinutes(EXPIRY_MINUTES))
                    .used(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            tokenRepository.save(row);
            // TODO: send via email provider. Until then, log for local/dev use.
            logger.info("Password reset token for {}: {}", email, token);
        });
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordValidator.validate(newPassword);

        PasswordResetTokenRow row = tokenRepository.findByTokenHash(hash(token))
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired reset token."));

        if (Boolean.TRUE.equals(row.getUsed()) || row.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid or expired reset token.");
        }

        UserRow user = row.getUser();
        user.setPassword(newPassword);
        userRepository.save(user);

        row.setUsed(true);
        tokenRepository.save(row);
        logger.info("Password reset completed for user {}.", user.getId());
    }

    private String generateToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return HexFormat.of().formatHex(bytes);
    }

    private String hash(String token) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(token.getBytes()));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to hash reset token", e);
        }
    }
}
