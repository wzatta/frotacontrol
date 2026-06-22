package com.cilazatta.frotacontrol.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cilazatta.frotacontrol.dto.ViagemPosicaoRequestDto;
import com.cilazatta.frotacontrol.dto.ViagemPosicaoResponseDto;
import com.cilazatta.frotacontrol.service.ViagemPosicaoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/viagens")
@RequiredArgsConstructor
public class ViagemPosicaoController {

	private final ViagemPosicaoService service;

	@PostMapping("/{viagemId}/posicoes")
	public ResponseEntity<ViagemPosicaoResponseDto> registrarPosicao(@PathVariable Long viagemId,
			@RequestBody @Valid ViagemPosicaoRequestDto request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarPosicao(viagemId, request));
	}

	@GetMapping("/{viagemId}/posicoes")
	public ResponseEntity<List<ViagemPosicaoResponseDto>> listarPosicoes(@PathVariable Long viagemId) {

		return ResponseEntity.ok(service.listarPorViagem(viagemId));
	}
}