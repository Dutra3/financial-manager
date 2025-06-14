package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;

public class Installment {

    private BigDecimal amount;

    public Installment(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal amount() {
        return amount;
    }
}
