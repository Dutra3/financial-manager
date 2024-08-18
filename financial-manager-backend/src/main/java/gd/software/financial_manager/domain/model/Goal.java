package gd.software.financial_manager.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Goal {

    private UUID id;
    private String name;
    private String description;
    private LocalDate targetDate;
    private BigDecimal targetAmount;
    private Boolean isAchieved;

    public Goal(UUID id, String name, String description, LocalDate targetDate, BigDecimal targetAmount, Boolean isAchieved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.targetDate = targetDate;
        this.targetAmount = targetAmount;
        this.isAchieved = isAchieved;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public LocalDate targetDate() {
        return targetDate;
    }

    public BigDecimal targetAmount() {
        return targetAmount;
    }

    public Boolean isAchieved() {
        return isAchieved;
    }
}
