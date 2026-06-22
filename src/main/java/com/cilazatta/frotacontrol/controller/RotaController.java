package com.cilazatta.frotacontrol.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cilazatta.frotacontrol.dto.RotaRequestDto;
import com.cilazatta.frotacontrol.dto.RotaResponseDto;
import com.cilazatta.frotacontrol.service.RotaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rotas")
@RequiredArgsConstructor
public class RotaController {

    private final RotaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RotaResponseDto salvar(
            @Valid @RequestBody RotaRequestDto request) {

        return service.salvar(request);
    }

    @GetMapping
    public List<RotaResponseDto> listar() {

        return service.listar();
    }

    @GetMapping("/{id}")
    public RotaResponseDto buscarPorId(
            @PathVariable Long id) {

        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public RotaResponseDto atualizar(
            @PathVariable Long id,
            @Valid @RequestBody RotaRequestDto request) {

        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @PathVariable Long id) {

        service.excluir(id);
    }
}