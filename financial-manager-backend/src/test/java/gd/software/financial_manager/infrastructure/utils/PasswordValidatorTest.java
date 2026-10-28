package gd.software.financial_manager.infrastructure.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PasswordValidatorTest {

    @Test
    void should_accept_valid_password() {
        assertThatCode(() -> PasswordValidator.validate("Password1!"))
                .doesNotThrowAnyException();
    }

    @Test
    void should_reject_short_password() {
        assertThatThrownBy(() -> PasswordValidator.validate("Pa1!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("at least 8 characters");
    }

    @Test
    void should_reject_password_without_uppercase() {
        assertThatThrownBy(() -> PasswordValidator.validate("password1!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("uppercase");
    }

    @Test
    void should_reject_password_without_digit() {
        assertThatThrownBy(() -> PasswordValidator.validate("Password!"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("digit");
    }

    @Test
    void should_reject_password_without_special_character() {
        assertThatThrownBy(() -> PasswordValidator.validate("Password1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("special");
    }

    @Test
    void should_reject_null_password() {
        assertThatThrownBy(() -> PasswordValidator.validate(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
