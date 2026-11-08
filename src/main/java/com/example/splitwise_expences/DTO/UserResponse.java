package com.example.splitwise_expences.DTO;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(UUID id, String name, String email, Instant createdAt, Instant updatedAt) {

	public static UserResponse from(UserEntity user) {
		return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(),
				user.getUpdatedAt());
	}
}
