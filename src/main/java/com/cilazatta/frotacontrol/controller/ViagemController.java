package com.cilazatta.frotacontrol.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cilazatta.frotacontrol.dto.ViagemEncerrarRequestDto;
import com.cilazatta.frotacontrol.dto.ViagemRequestDto;
import com.cilazatta.frotacontrol.dto.ViagemResponseDto;
import com.cilazatta.frotacontrol.service.ViagemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/viagens")
@RequiredArgsConstructor
public class ViagemController {

    private final ViagemService service;

    // =========================================
    // ABRIR VIAGEM
    // =========================================
    @PostMapping
    public ResponseEntity<ViagemResponseDto> abrirViagem(
            @RequestBody @Valid ViagemRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.abrirViagem(request));
    }

    // =========================================
    // ENCERRAR VIAGEM
    // =========================================
    @PutMapping("/{id}/encerrar")
    public ResponseEntity<ViagemResponseDto> encerrarViagem(
            @PathVariable Long id,
            @RequestBody @Valid ViagemEncerrarRequestDto request) {

        return ResponseEntity.ok(
                service.encerrarViagem(id, request));
    }

    // =========================================
    // BUSCAR POR ID
    // =========================================
    @GetMapping("/{id}")
    public ResponseEntity<ViagemResponseDto> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id));
    }

    // =========================================
    // LISTAR VIAGENS
    // =========================================
    @GetMapping
    public ResponseEntity<List<ViagemResponseDto>> listar() {

        return ResponseEntity.ok(
                service.listar());
    }
}
