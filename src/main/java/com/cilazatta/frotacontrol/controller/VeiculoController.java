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

import com.cilazatta.frotacontrol.dto.VeiculoRequestDto;
import com.cilazatta.frotacontrol.dto.VeiculoResponseDto;
import com.cilazatta.frotacontrol.service.VeiculoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoResponseDto salvar(
            @Valid @RequestBody VeiculoRequestDto request) {

        return service.salvar(request);
    }

    @GetMapping
    public List<VeiculoResponseDto> listar() {

        return service.listar();
    }

    @GetMapping("/{id}")
    public VeiculoResponseDto buscarPorId(
            @PathVariable Long id) {

        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public VeiculoResponseDto atualizar(
            @PathVariable Long id,
            @Valid @RequestBody VeiculoRequestDto request) {

        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @PathVariable Long id) {

        service.excluir(id);
    }
}
