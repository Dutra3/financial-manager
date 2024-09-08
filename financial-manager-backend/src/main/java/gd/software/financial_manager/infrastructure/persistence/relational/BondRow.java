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
@Table(name = "gd_bond")
public class BondRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String ticker;

    @Column(length = 255)
    private String description;

    @Column(precision = 8, scale = 4)
    private BigDecimal quantity;

    @Column(precision = 8, scale = 4)
    private BigDecimal price;
}
