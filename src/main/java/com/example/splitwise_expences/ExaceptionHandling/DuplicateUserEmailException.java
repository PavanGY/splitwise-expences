package com.example.splitwise_expences.user;

public class DuplicateUserEmailException extends RuntimeException {

	public DuplicateUserEmailException(String email) {
		super("A user with email " + email + " already exists");
	}
}
