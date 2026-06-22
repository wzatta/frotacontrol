package com.cilazatta.frotacontrol.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ViagemPosicaoResponseDto {

    private Long id;

    private Double latitude;

    private Double longitude;

    private Double velocidade;

    private LocalDateTime dataHora;
}