package com.cilazatta.frotacontrol.mapper;

import org.springframework.stereotype.Component;

import com.cilazatta.frotacontrol.dto.RotaFuncionarioResponseDto;
import com.cilazatta.frotacontrol.entity.RotaFuncionario;

@Component
public class RotaFuncionarioMapper {

    public RotaFuncionarioResponseDto toResponse(
            RotaFuncionario entity) {

        RotaFuncionarioResponseDto dto =
                new RotaFuncionarioResponseDto();

        dto.setId(entity.getId());

        dto.setFuncionarioId(
                entity.getFuncionario().getId());

        dto.setNomeFuncionario(
                entity.getFuncionario().getNome());

        dto.setOrdem(entity.getOrdem());

        dto.setAtivo(entity.getAtivo());

        return dto;
    }



}