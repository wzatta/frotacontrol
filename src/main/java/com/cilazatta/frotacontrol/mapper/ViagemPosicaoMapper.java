package com.cilazatta.frotacontrol.mapper;


import org.springframework.stereotype.Component;

import com.cilazatta.frotacontrol.dto.ViagemPosicaoResponseDto;
import com.cilazatta.frotacontrol.entity.ViagemPosicao;

@Component
public class ViagemPosicaoMapper {

    public ViagemPosicaoResponseDto toResponse(ViagemPosicao entity) {

        ViagemPosicaoResponseDto dto =   new ViagemPosicaoResponseDto();

        dto.setId(entity.getId());

        dto.setLatitude(entity.getLatitude());

        dto.setLongitude(entity.getLongitude());

        dto.setVelocidade(entity.getVelocidade());

        dto.setDataHora(entity.getDataHora());

        return dto;
    }
}