package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Profile {

    private UUID id;
    private String name;
    private String profession;
    private BigDecimal netSalary;
    private Integer payday;
    private BigDecimal initialBalance;
    private BigDecimal financialGoal;

    public Profile(UUID id, String name, String profession, BigDecimal netSalary, Integer payday, BigDecimal initialBalance,
                   BigDecimal financialGoal) {
        this.id = id;
        this.name = name;
        this.profession = profession;
        this.netSalary = netSalary;
        this.payday = payday;
        this.initialBalance = initialBalance;
        this.financialGoal = financialGoal;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String profession() {
        return profession;
    }

    public BigDecimal netSalary() {
        return netSalary;
    }

    public Integer payday() {
        return payday;
    }

    public BigDecimal financialGoal() {
        return financialGoal;
    }

    public BigDecimal initialBalance() {
        return initialBalance;
    }
}
