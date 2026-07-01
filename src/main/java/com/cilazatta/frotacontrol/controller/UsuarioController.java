package com.cilazatta.frotacontrol.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cilazatta.frotacontrol.dto.UsuarioNomeResponseDto;
import com.cilazatta.frotacontrol.dto.UsuarioRequestDto;
import com.cilazatta.frotacontrol.dto.UsuarioResponseDto;
import com.cilazatta.frotacontrol.dto.UsuarioUpdateRequestDto;
import com.cilazatta.frotacontrol.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
	
	 private final UsuarioService usuarioService;

	    // 🔹 Construtor explícito
	    public UsuarioController(UsuarioService registerService) {
	        this.usuarioService = registerService;
	    }
	    
	    /**
	     * Endpoint para criar um novo usuário.
	     */
	    @PostMapping
	    public ResponseEntity<UsuarioResponseDto> createUser(@Valid @RequestBody UsuarioRequestDto dto) {
	        UsuarioResponseDto created = usuarioService.createUser(dto);
	        log.info("Usuário criado via API: {}", created.getLogin());
	        return ResponseEntity.status(HttpStatus.CREATED).body(created);
	    }
	    
	    /**
	     * Endpoint para atualizar informações do usuário (exceto senha).
	     */
	    @PutMapping("/{id}")
	    public ResponseEntity<UsuarioResponseDto> updateUser(
	            @PathVariable Long id,
	            @Valid @RequestBody UsuarioUpdateRequestDto dto) {
	        UsuarioResponseDto updated = usuarioService.updateUser(id, dto);
	        log.info("Usuário atualizado via API: {}", updated.getLogin());
	        return ResponseEntity.ok(updated);
	    }

	    @GetMapping
	    public ResponseEntity<UsuarioNomeResponseDto> buscarNomeUsuario(){
	    
	    	return ResponseEntity.ok(usuarioService.buscarNomeUsuario());
	    }
}
