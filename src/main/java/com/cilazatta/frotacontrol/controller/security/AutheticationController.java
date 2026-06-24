package com.cilazatta.frotacontrol.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cilazatta.frotacontrol.dto.AutheticateRequestDto;
import com.cilazatta.frotacontrol.dto.AutheticationResponseDto;
import com.cilazatta.frotacontrol.dto.RegisterRequestDto;
import com.cilazatta.frotacontrol.security.service.AutheticationService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/auth")
public class AutheticationController {
	
	@Autowired
	private AutheticationService service;
	
	@PostMapping("/register")
	public ResponseEntity<AutheticationResponseDto> register(
			@RequestBody RegisterRequestDto requestRegister
			){
		return ResponseEntity.ok(service.register(requestRegister));
	}

	@PostMapping("/authenticate")
	 public ResponseEntity<AutheticationResponseDto> authenticate(
	            @Valid @RequestBody AutheticateRequestDto request) {

	        AutheticationResponseDto response = service.authetication(request);

	        if (response == null || response.getToken() == null || response.getToken().isBlank()) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	        }

	        return ResponseEntity.ok(response);
	    }
	}
	

