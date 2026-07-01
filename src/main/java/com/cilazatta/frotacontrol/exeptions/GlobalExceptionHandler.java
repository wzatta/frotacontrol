package com.cilazatta.frotacontrol.exeptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {

		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusiness(BusinessException ex) {

		return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

		String message = String.format("O parâmetro '%s' deve ser um número válido.", ex.getName());

		return buildResponse(HttpStatus.BAD_REQUEST, message);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {

		String message = ex.getConstraintViolations().stream().findFirst().map(v -> v.getMessage())
				.orElse("Erro de validação");

		return buildResponse(HttpStatus.BAD_REQUEST, message);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrity(DataIntegrityViolationException ex) {

		return buildResponse(HttpStatus.BAD_REQUEST, "Violação de integridade dos dados.");
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {

	    return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
	}

	private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {

		Map<String, Object> body = new HashMap<>();

		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());
		body.put("message", message);

		return ResponseEntity.status(status).body(body);
	}
}