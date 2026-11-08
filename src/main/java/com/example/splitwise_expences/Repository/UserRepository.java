package com.example.splitwise_expences.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import com.example.splitwise_expences.DTO.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
	boolean existsByEmail(String email);

	boolean existsByEmailAndIdNot(String email, UUID id);
}
