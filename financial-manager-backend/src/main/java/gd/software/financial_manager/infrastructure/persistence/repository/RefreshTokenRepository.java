package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.RefreshTokenRow;
import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenRow, UUID> {

    Optional<RefreshTokenRow> findByTokenHash(String tokenHash);

    List<RefreshTokenRow> findAllByUserAndRevokedFalse(UserRow user);

    void deleteAllByExpiresAtBefore(Instant instant);
}
