package gd.software.financial_manager.infrastructure.utils;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static UUID currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof String principal)) {
            return null;
        }
        try {
            return UUID.fromString(principal);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static UUID resolveUserId(UUID requested) {
        UUID authenticated = currentUserId();
        return authenticated != null ? authenticated : requested;
    }

    public static void requireSameUser(UUID userId) {
        UUID authenticated = currentUserId();
        if (authenticated != null && !authenticated.equals(userId)) {
            throw new AccessDeniedException("Authenticated user cannot access another user's data.");
        }
    }

    public static void requireOwner(UUID ownerId) {
        if (ownerId == null) {
            return;
        }
        requireSameUser(ownerId);
    }
}
