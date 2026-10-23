package gd.software.financial_manager.infrastructure.utils;

import gd.software.financial_manager.infrastructure.persistence.relational.RefreshTokenRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import gd.software.financial_manager.infrastructure.persistence.repository.RefreshTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Optional;

@Service
public class RefreshTokenService {

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);
    private static final long REFRESH_TOKEN_DAYS = 7;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public String create(UserRow user) {
        byte[] bytes = new byte[48];
        SECURE_RANDOM.nextBytes(bytes);
        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        refreshTokenRepository.save(RefreshTokenRow.builder()
                .user(user)
                .tokenHash(hash(rawToken))
                .expiresAt(Instant.now().plus(REFRESH_TOKEN_DAYS, ChronoUnit.DAYS))
                .revoked(false)
                .build());

        return rawToken;
    }

    @Transactional
    public Optional<RefreshTokenRow> rotate(String rawToken) {
        Optional<RefreshTokenRow> found = refreshTokenRepository.findByTokenHash(hash(rawToken));

        if (found.isEmpty()) {
            return Optional.empty();
        }

        RefreshTokenRow token = found.get();

        if (token.isRevoked()) {
            logger.warn("Reused revoked refresh token for user {}. Revoking all sessions.", token.getUser().getId());
            revokeAllForUser(token.getUser());
            return Optional.empty();
        }

        if (token.getExpiresAt().isBefore(Instant.now())) {
            return Optional.empty();
        }

        token.setRevoked(true);
        refreshTokenRepository.save(token);
        return Optional.of(token);
    }

    @Transactional
    public void revoke(String rawToken) {
        refreshTokenRepository.findByTokenHash(hash(rawToken))
                .ifPresent(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                });
    }

    private void revokeAllForUser(UserRow user) {
        refreshTokenRepository.findAllByUserAndRevokedFalse(user)
                .forEach(token -> {
                    token.setRevoked(true);
                    refreshTokenRepository.save(token);
                });
    }

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void cleanupExpired() {
        refreshTokenRepository.deleteAllByExpiresAtBefore(Instant.now());
    }

    public static String hash(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(rawToken.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
