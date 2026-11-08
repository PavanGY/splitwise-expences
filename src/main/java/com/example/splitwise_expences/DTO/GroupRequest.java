package com.example.splitwise_expences.DTO;

import jakarta.validation.constraints.NotBlank;

public record GroupRequest(
        @NotBlank(message = "Group name is required")
        String name,

        String description
) {}