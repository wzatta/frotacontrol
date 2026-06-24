package com.cilazatta.frotacontrol.controller.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cilazatta.frotacontrol.dto.RegisterRequestDto;
import com.cilazatta.frotacontrol.entity.Usuario;
import com.cilazatta.frotacontrol.security.service.RegisterRequestService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/v1/register")
public class RegisterRequestController {
	
	 private final RegisterRequestService registerService;

	    // 🔹 Construtor explícito
	    public RegisterRequestController(RegisterRequestService registerService) {
	        this.registerService = registerService;
	    }
	    
	    /**
	     * Endpoint para criar um novo usuário.
	     */
	    @PostMapping
	    public ResponseEntity<Usuario> createUser(@Valid @RequestBody RegisterRequestDto dto) {
	        Usuario created = registerService.createUser(dto);
	        log.info("Usuário criado via API: {}", created.getLogin());
	        return ResponseEntity.status(HttpStatus.CREATED).body(created);
	    }
	    
	    /**
	     * Endpoint para atualizar informações do usuário (exceto senha).
	     */
	    @PutMapping("/{id}")
	    public ResponseEntity<Usuario> updateUser(
	            @PathVariable Long id,
	            @Valid @RequestBody RegisterRequestDto dto) {
	        Usuario updated = registerService.updateUser(id, dto);
	        log.info("Usuário atualizado via API: {}", updated.getLogin());
	        return ResponseEntity.ok(updated);
	    }

	    
}
