package gd.software.financial_manager.infrastructure.persistence.repository;

import gd.software.financial_manager.infrastructure.persistence.relational.TransactionRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionRow, UUID> {

    List<TransactionRow> findByUserId(UUID id);

    @Query(value = """
            SELECT transaction.amount FROM TransactionRow transaction
            WHERE transaction.user.id = :id
            AND transaction.category.type = :type
            """)
    List<BigDecimal> findByUserIdAndType(@Param("id") UUID id, @Param("type") String type);
}
