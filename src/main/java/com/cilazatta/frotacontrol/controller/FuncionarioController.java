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

import com.cilazatta.frotacontrol.dto.FuncionarioRequestDto;
import com.cilazatta.frotacontrol.dto.FuncionarioResponseDto;
import com.cilazatta.frotacontrol.service.FuncionarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioResponseDto salvar(
            @Valid @RequestBody FuncionarioRequestDto request) {

        return service.salvar(request);
    }

    @GetMapping
    public List<FuncionarioResponseDto> listar() {

        return service.listar();
    }

    @GetMapping("/{id}")
    public FuncionarioResponseDto buscarPorId(
            @PathVariable Long id) {

        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public FuncionarioResponseDto atualizar(
            @PathVariable Long id,
            @Valid @RequestBody FuncionarioRequestDto request) {

        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @PathVariable Long id) {

        service.excluir(id);
    }
}