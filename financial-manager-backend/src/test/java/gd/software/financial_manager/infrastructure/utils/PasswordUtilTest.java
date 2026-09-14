package gd.software.financial_manager.infrastructure.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordUtilTest {

    @Test
    void encryptPassword_should_return_hash_different_from_raw() {
        String raw = "mySecret123";

        String encrypted = PasswordUtil.encryptPassword(raw);

        assertThat(encrypted).isNotEqualTo(raw);
        assertThat(encrypted).isNotBlank();
    }

    @Test
    void encryptPassword_should_return_different_hashes_for_same_input() {
        String raw = "mySecret123";

        String first = PasswordUtil.encryptPassword(raw);
        String second = PasswordUtil.encryptPassword(raw);

        assertThat(first).isNotEqualTo(second);
    }

    @Test
    void matches_should_return_true_when_password_matches_encrypted() {
        String raw = "mySecret123";
        String encrypted = PasswordUtil.encryptPassword(raw);

        assertThat(PasswordUtil.matches(raw, encrypted)).isTrue();
    }

    @Test
    void matches_should_return_false_when_password_does_not_match() {
        String encrypted = PasswordUtil.encryptPassword("correctPassword");

        assertThat(PasswordUtil.matches("wrongPassword", encrypted)).isFalse();
    }
}
