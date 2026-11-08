package com.example.splitwise_expences.Service;

import com.example.splitwise_expences.DTO.UserRequest;
import com.example.splitwise_expences.DTO.UserResponse;
import com.example.splitwise_expences.DTO.UserEntity;
import com.example.splitwise_expences.ExceptionHandling.DuplicateUserEmailException;
import com.example.splitwise_expences.ExceptionHandling.UserNotFoundException;
import com.example.splitwise_expences.Repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;

	UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserResponse create(UserRequest request) {
		String name = request.name().trim();
		String email = normalizeEmail(request.email());
		if (userRepository.existsByEmail(email)) {
			throw new DuplicateUserEmailException(email);
		}

		try {
			return UserResponse.from(userRepository.saveAndFlush(new UserEntity(name, email)));
		}
		catch (DataIntegrityViolationException exception) {
			throw new DuplicateUserEmailException(email);
		}
	}

	@Transactional(readOnly = true)
	public List<UserResponse> findAll() {
		return userRepository.findAll().stream().map(UserResponse::from).toList();
	}

	@Transactional(readOnly = true)
	public UserResponse findById(UUID id) {
		return UserResponse.from(findUser(id));
	}

	public UserResponse update(UUID id, UserRequest request) {
		UserEntity user = findUser(id);
		String name = request.name().trim();
		String email = normalizeEmail(request.email());
		if (userRepository.existsByEmailAndIdNot(email, id)) {
			throw new DuplicateUserEmailException(email);
		}

		user.update(name, email);
		try {
			return UserResponse.from(userRepository.saveAndFlush(user));
		}
		catch (DataIntegrityViolationException exception) {
			throw new DuplicateUserEmailException(email);
		}
	}

	public void delete(UUID id) {
		userRepository.delete(findUser(id));
	}

	private UserEntity findUser(UUID id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	private String normalizeEmail(String email) {
		return email.trim().toLowerCase(Locale.ROOT);
	}
}
