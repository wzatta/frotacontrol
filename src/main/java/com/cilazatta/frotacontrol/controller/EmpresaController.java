package com.cilazatta.frotacontrol.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cilazatta.frotacontrol.dto.EmpresaRequestDto;
import com.cilazatta.frotacontrol.dto.EmpresaResponseDto;
import com.cilazatta.frotacontrol.service.EmpresaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/empresas")
@Validated
@RequiredArgsConstructor
public class EmpresaController {

	private final EmpresaService service;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<EmpresaResponseDto> criar(@RequestBody @Valid EmpresaRequestDto request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<EmpresaResponseDto> atualizar(@PathVariable Long id,
			@RequestBody @Valid EmpresaRequestDto request) {

		return ResponseEntity.ok(service.atualizar(id, request));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<EmpresaResponseDto> buscarPorId(@PathVariable @Positive(message = "Id deve ser maior que zero") Long id) {

		return ResponseEntity.ok(service.buscarPorId(id));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<EmpresaResponseDto>> listar() {

		return ResponseEntity.ok(service.listar());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {

		service.excluir(id);

		return ResponseEntity.noContent().build();
	}
}
