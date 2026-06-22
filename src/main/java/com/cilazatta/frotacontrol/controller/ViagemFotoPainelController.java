package com.cilazatta.frotacontrol.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cilazatta.frotacontrol.dto.ViagemFotoPainelRequestDto;
import com.cilazatta.frotacontrol.dto.ViagemFotoPainelResponseDto;
import com.cilazatta.frotacontrol.service.ViagemFotoPainelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/viagens")
@RequiredArgsConstructor
public class ViagemFotoPainelController {

	private final ViagemFotoPainelService service;

	@PostMapping("/{viagemId}/fotos")
	@ResponseStatus(HttpStatus.CREATED)
	public ViagemFotoPainelResponseDto registrarFoto(@PathVariable Long viagemId,
			@RequestBody @Valid ViagemFotoPainelRequestDto request) {

		return service.registrarFoto(viagemId, request);
	}

	@GetMapping("/{viagemId}/fotos")
	public List<ViagemFotoPainelResponseDto> listarFotos(@PathVariable Long viagemId) {

		return service.listarPorViagem(viagemId);
	}
}