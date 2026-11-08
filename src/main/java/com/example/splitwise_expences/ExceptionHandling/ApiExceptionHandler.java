package com.example.splitwise_expences.ExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Map;
import java.util.TreeMap;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	ProblemDetail handleNotFound(UserNotFoundException exception) {
		return problem(HttpStatus.NOT_FOUND, "User not found", exception.getMessage());
	}

	@ExceptionHandler(DuplicateUserEmailException.class)
	ProblemDetail handleDuplicateEmail(DuplicateUserEmailException exception) {
		return problem(HttpStatus.CONFLICT, "Email already registered", exception.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ProblemDetail handleValidation(MethodArgumentNotValidException exception) {
		ProblemDetail problem = problem(HttpStatus.BAD_REQUEST, "Invalid request",
				"One or more request fields are invalid");
		Map<String, String> errors = new TreeMap<>();
		exception.getBindingResult().getFieldErrors()
				.forEach(error -> errors.putIfAbsent(error.getField(), error.getDefaultMessage()));
		problem.setProperty("fieldErrors", errors);
		return problem;
	}

	private ProblemDetail problem(HttpStatus status, String title, String detail) {
		ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
		problem.setTitle(title);
		problem.setType(URI.create("about:blank"));
		return problem;
	}
}
