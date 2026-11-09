package com.example.splitwise_expences.DTO;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "expense_groups")

public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Default constructor required by JPA
    public GroupEntity() {}

    public GroupEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
