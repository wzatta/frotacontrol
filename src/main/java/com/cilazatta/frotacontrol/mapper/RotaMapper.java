package com.cilazatta.frotacontrol.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cilazatta.frotacontrol.dto.RotaResponseDto;
import com.cilazatta.frotacontrol.entity.Rota;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RotaMapper {

    private final RotaFuncionarioMapper rotaFuncionarioMapper;

    public RotaResponseDto toResponse(Rota entity) {

        RotaResponseDto dto = new RotaResponseDto();

        dto.setId(entity.getId());
        dto.setDescricao(entity.getDescricao());
        dto.setAtiva(entity.getAtiva());

        dto.setPassageiros(
                entity.getPassageiros()
                        .stream()
                        .map(rotaFuncionarioMapper::toResponse)
                        .collect(Collectors.toList())
        );

        return dto;
    }
}