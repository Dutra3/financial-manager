package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.UserRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserRow, UUID> {

    Optional<UserRow> findByEmail(String email);

    Optional<UserRow> findByProviderAndProviderId(String provider, String providerId);
}
