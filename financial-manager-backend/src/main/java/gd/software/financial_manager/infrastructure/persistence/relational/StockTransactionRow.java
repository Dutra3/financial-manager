package gd.software.financial_manager.infrastructure.persistence.relational;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "gd_stock_transaction")
public class StockTransactionRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    @ToString.Exclude
    private StockRow stock;

    @Column(precision = 8, scale = 4)
    private BigDecimal quantity;

    @Column(precision = 8, scale = 4)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDate transactionDate;
}