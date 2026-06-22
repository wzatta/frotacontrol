package com.cilazatta.frotacontrol.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ViagemEncerrarResponseDto {

    private Long viagemId;

    private Long kmInicial;

    private Long kmFinal;

    private Long kmPercorrido;

    private LocalDateTime dataHoraSaida;

    private LocalDateTime dataHoraChegada;

    private String status;
}
