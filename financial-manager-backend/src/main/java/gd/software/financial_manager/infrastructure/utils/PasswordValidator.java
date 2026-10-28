package gd.software.financial_manager.infrastructure.utils;

public final class PasswordValidator {

    private static final String SPECIAL_CHARS = "!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?";

    private PasswordValidator() {
    }

    public static void validate(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("Password must contain at least one digit.");
        }
        if (!password.matches(".*[" + SPECIAL_CHARS + "].*")) {
            throw new IllegalArgumentException("Password must contain at least one special character.");
        }
    }
}
