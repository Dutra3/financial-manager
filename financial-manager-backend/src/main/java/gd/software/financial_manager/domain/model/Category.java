package gd.software.financial_manager.domain.model;

import java.util.UUID;

public class Category {

    private UUID id;
    private String name;
    private CategoryType type;

    public Category(UUID id, String name, CategoryType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public CategoryType type() {
        return type;
    }
}
