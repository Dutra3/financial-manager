package gd.software.financial_manager.infrastructure.persistence.relational;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "gd_profile")
public class ProfileRow {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 100)
    private String profession;

    @Column(precision = 13, scale = 4, nullable = false)
    private BigDecimal netSalary;

    @Column(nullable = false)
    private Integer payday;

    @Column(precision = 13, scale = 4, nullable = false)
    private BigDecimal initialBalance;

    @Column(precision = 13, scale = 4, nullable = false)
    private BigDecimal financialGoal;

    @Column(length = 10)
    private String currency;

    @Column(length = 20)
    private String theme;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserRow user;
}
