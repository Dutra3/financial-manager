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
@Table(name = "gd_stock")
public class StockRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 30)
    private String ticker;

    @Column(length = 255)
    private String description;

    @Column(length = 30)
    private String type;

    @Column(length = 255)
    private String industrySegment;

    @Column(precision = 4, scale = 2)
    private BigDecimal tagAlong;

    @Column(precision = 8, scale = 4)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean isBesst;

    @Column(nullable = false)
    private Boolean isNewMarket;
}
