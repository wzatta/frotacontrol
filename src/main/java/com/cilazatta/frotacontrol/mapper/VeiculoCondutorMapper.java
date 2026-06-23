package com.cilazatta.frotacontrol.mapper;

import org.springframework.stereotype.Component;

import com.cilazatta.frotacontrol.dto.VeiculoCondutorRequestDto;
import com.cilazatta.frotacontrol.dto.VeiculoCondutorResponseDto;
import com.cilazatta.frotacontrol.entity.VeiculoCondutor;

@Component
public class VeiculoCondutorMapper{

    public VeiculoCondutorResponseDto toResponse(
            VeiculoCondutor entity) {

        VeiculoCondutorResponseDto dto =
                new VeiculoCondutorResponseDto();

        dto.setId(entity.getId());

        dto.setVeiculoId(
                entity.getVeiculo().getId());

        dto.setPlaca(
                entity.getVeiculo().getPlaca());

        dto.setFuncionarioId(
                entity.getFuncionario().getId());

        dto.setFuncionarioNome(
                entity.getFuncionario().getNome());

        dto.setPrincipal(
                entity.getPrincipal());

        dto.setAtivo(
                entity.getAtivo());

        dto.setDataInicio(
                entity.getDataInicio());

        dto.setDataFim(
                entity.getDataFim());

        return dto;
    }

    public void updateEntity(
            VeiculoCondutor entity,
            VeiculoCondutorRequestDto request) {

        entity.setPrincipal(
                request.getPrincipal());

        entity.setAtivo(
                request.getAtivo());

        entity.setDataInicio(
                request.getDataInicio());

        entity.setDataFim(
                request.getDataFim());
    }
}
