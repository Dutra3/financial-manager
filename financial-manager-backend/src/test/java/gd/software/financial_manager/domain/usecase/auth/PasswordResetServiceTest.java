package gd.software.financial_manager.domain.usecase.auth;

import gd.software.financial_manager.infrastructure.persistence.relational.PasswordResetTokenRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import gd.software.financial_manager.infrastructure.persistence.repository.PasswordResetTokenRepository;
import gd.software.financial_manager.infrastructure.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetServiceTest {

    @Mock
    private PasswordResetTokenRepository tokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PasswordResetService passwordResetService;

    private static final String STRONG_PASSWORD = "Senha@123";

    private UserRow user() {
        UserRow user = new UserRow();
        user.setId(UUID.randomUUID());
        user.setEmail("a@b.com");
        return user;
    }

    private PasswordResetTokenRow token(UserRow user, boolean used, LocalDateTime expiresAt) {
        return PasswordResetTokenRow.builder()
                .id(UUID.randomUUID())
                .user(user)
                .tokenHash("hash")
                .expiresAt(expiresAt)
                .used(used)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void should_create_token_for_known_email() {
        when(userRepository.findByEmail("a@b.com")).thenReturn(Optional.of(user()));

        passwordResetService.requestReset("a@b.com");

        verify(tokenRepository).save(any(PasswordResetTokenRow.class));
    }

    @Test
    void should_stay_silent_for_unknown_email() {
        when(userRepository.findByEmail("ghost@x.com")).thenReturn(Optional.empty());

        passwordResetService.requestReset("ghost@x.com");

        verify(tokenRepository, never()).save(any());
    }

    @Test
    void should_reset_password_with_valid_token() {
        UserRow user = user();
        PasswordResetTokenRow row = token(user, false, LocalDateTime.now().plusMinutes(10));
        when(tokenRepository.findByTokenHash(anyString())).thenReturn(Optional.of(row));

        passwordResetService.resetPassword("some-token", STRONG_PASSWORD);

        verify(userRepository).save(user);
        assert row.getUsed();
        verify(tokenRepository).save(row);
    }

    @Test
    void should_reject_used_token() {
        PasswordResetTokenRow row = token(user(), true, LocalDateTime.now().plusMinutes(10));
        when(tokenRepository.findByTokenHash(anyString())).thenReturn(Optional.of(row));

        assertThatThrownBy(() -> passwordResetService.resetPassword("t", STRONG_PASSWORD))
                .isInstanceOf(IllegalArgumentException.class);
        verify(userRepository, never()).save(any());
    }

    @Test
    void should_reject_expired_token() {
        PasswordResetTokenRow row = token(user(), false, LocalDateTime.now().minusMinutes(1));
        when(tokenRepository.findByTokenHash(anyString())).thenReturn(Optional.of(row));

        assertThatThrownBy(() -> passwordResetService.resetPassword("t", STRONG_PASSWORD))
                .isInstanceOf(IllegalArgumentException.class);
        verify(userRepository, never()).save(any());
    }

    @Test
    void should_reject_weak_password() {
        assertThatThrownBy(() -> passwordResetService.resetPassword("t", "123"))
                .isInstanceOf(IllegalArgumentException.class);
        verify(tokenRepository, never()).findByTokenHash(anyString());
    }
}
