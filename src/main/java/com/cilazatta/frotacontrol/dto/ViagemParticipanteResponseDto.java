package com.cilazatta.frotacontrol.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ViagemParticipanteResponseDto {

    private Long id;

    private Long funcionarioId;

    private String nomeFuncionario;

    private Boolean presente;

    private LocalDateTime dataHoraConfirmacao;

}