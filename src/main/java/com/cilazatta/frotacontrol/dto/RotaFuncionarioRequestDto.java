package com.cilazatta.frotacontrol.dto;

import lombok.Data;

@Data
public class RotaFuncionarioRequestDto {

    private Long funcionarioId;

    private Integer ordem;

    private Boolean ativo;
}