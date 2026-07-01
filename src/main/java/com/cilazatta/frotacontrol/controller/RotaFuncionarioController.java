package com.cilazatta.frotacontrol.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cilazatta.frotacontrol.dto.RotaFuncionarioRequestDto;
import com.cilazatta.frotacontrol.dto.RotaFuncionarioResponseDto;
import com.cilazatta.frotacontrol.service.RotaFuncionarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/rotas/{rotaId}/funcionarios")
@RequiredArgsConstructor
public class RotaFuncionarioController {

    private final RotaFuncionarioService service;

    @PostMapping
    public ResponseEntity<RotaFuncionarioResponseDto> adicionar(
            @PathVariable Long rotaId,
            @RequestBody @Valid RotaFuncionarioRequestDto dto) {

        return ResponseEntity.ok(service.adicionar(rotaId, dto));
    }

    @GetMapping
    public ResponseEntity<List<RotaFuncionarioResponseDto>> listar(@PathVariable Long rotaId) {

        return ResponseEntity.ok(service.listarPorRota(rotaId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long rotaId,
                                        @PathVariable Long id) {

        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}