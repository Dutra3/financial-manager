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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(precision = 8, scale = 4)
    private BigDecimal amount;

    @ManyToMany
    @JoinTable(
            name = "gd_wallet_bonds",
            joinColumns = @JoinColumn(name = "wallet_id"),
            inverseJoinColumns = @JoinColumn(name = "bond_id"))
    private List<BondRow> bonds;

    @ManyToMany
    @JoinTable(
            name = "gd_wallet_stocks",
            joinColumns = @JoinColumn(name = "wallet_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id"))
    private List<StockRow> stocks;

    @ManyToMany
    @JoinTable(
            name = "gd_wallet_reits",
            joinColumns = @JoinColumn(name = "wallet_id"),
            inverseJoinColumns = @JoinColumn(name = "reit_id"))
    private List<ReitRow> reits;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserRow user;
}
