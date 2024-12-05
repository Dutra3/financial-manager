package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;

public class Debit {

    private BigDecimal amount;

    public Debit(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal amount() {
        return amount;
    }
}
