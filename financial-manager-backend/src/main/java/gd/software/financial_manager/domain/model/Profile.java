package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Profile {

    private UUID id;
    private String name;
    private String profession;
    private BigDecimal netSalary;
    private LocalDate payDay;
    private BigDecimal initialBalance;
    private BigDecimal financialGoal;

    public Profile(UUID id, String name, String profession, BigDecimal netSalary, LocalDate payDay, BigDecimal financialGoal,
                   BigDecimal initialBalance) {
        this.id = id;
        this.name = name;
        this.profession = profession;
        this.netSalary = netSalary;
        this.payDay = payDay;
        this.financialGoal = financialGoal;
        this.initialBalance = initialBalance;
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

    public LocalDate payDay() {
        return payDay;
    }

    public BigDecimal financialGoal() {
        return financialGoal;
    }

    public BigDecimal initialBalance() {
        return initialBalance;
    }
}
