package com.cilazatta.frotacontrol.dto;


import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoCondutorResponseDto {

    private Long id;

    private Long veiculoId;
    private String placa;

    private Long funcionarioId;
    private String funcionarioNome;

    private Boolean principal;

    private Boolean ativo;

    private LocalDate dataInicio;

    private LocalDate dataFim;
}