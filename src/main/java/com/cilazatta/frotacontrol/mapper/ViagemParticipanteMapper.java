package com.cilazatta.frotacontrol.mapper;

import org.springframework.stereotype.Component;

import com.cilazatta.frotacontrol.dto.ViagemParticipanteResponseDto;
import com.cilazatta.frotacontrol.entity.ViagemParticipante;

@Component
public class ViagemParticipanteMapper {

    public ViagemParticipanteResponseDto toResponse(ViagemParticipante entity) {

        ViagemParticipanteResponseDto dto =
                new ViagemParticipanteResponseDto();

        dto.setId(entity.getId());

        dto.setFuncionarioId(
                entity.getFuncionario().getId());

        dto.setNomeFuncionario(
                entity.getFuncionario().getNome());

        dto.setPresente(
                entity.getPresente());

        dto.setDataHoraConfirmacao(
                entity.getDataHoraConfirmacao());

        return dto;
    }
}
