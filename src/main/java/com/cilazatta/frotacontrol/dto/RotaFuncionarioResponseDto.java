package com.cilazatta.frotacontrol.dto;

import lombok.Data;

@Data
public class RotaFuncionarioResponseDto {

    private Long id;

    private Long funcionarioId;

    private String nomeFuncionario;

    private Integer ordem;

    private Boolean ativo;
}