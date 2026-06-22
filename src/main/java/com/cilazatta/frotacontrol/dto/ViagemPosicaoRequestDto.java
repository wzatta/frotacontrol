package com.cilazatta.frotacontrol.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ViagemPosicaoRequestDto {

    private Long viagemId;

    private Double latitude;

    private Double longitude;

    private Double velocidade;

    private LocalDateTime dataHora;
}