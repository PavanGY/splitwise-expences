package com.example.splitwise_expences.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
		@NotBlank @Size(max = 120) String name,
		@NotBlank @Email @Size(max = 254) String email) {
}
