package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Profile {

    private UUID id;
    private String name;
    private String profession;
    private BigDecimal salary;
    private LocalDate payDay;
    private BigDecimal financialGoal;

    public Profile(UUID id, String name, String profession, BigDecimal salary, LocalDate payDay, BigDecimal financialGoal) {
        this.id = id;
        this.name = name;
        this.profession = profession;
        this.salary = salary;
        this.payDay = payDay;
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

    public BigDecimal salary() {
        return salary;
    }

    public LocalDate payDay() {
        return payDay;
    }

    public BigDecimal financialGoal() {
        return financialGoal;
    }
}
