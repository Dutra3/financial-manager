package gd.software.financial_manager.infrastructure.persistence.relational;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "gd_wallet")
public class WalletRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(precision = 8, scale = 4)
    private BigDecimal totalAmount;

    @OneToMany
    private List<BondRow> bonds;

    @OneToMany
    private List<StockRow> stocks;

    @OneToMany
    private List<ReitRow> reits;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserRow user;
}
