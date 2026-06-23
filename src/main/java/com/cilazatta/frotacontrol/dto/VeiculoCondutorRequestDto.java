package com.cilazatta.frotacontrol.dto;


import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoCondutorRequestDto {

    @NotNull
    private Long veiculoId;

    @NotNull
    private Long funcionarioId;

    private Boolean principal;

    private Boolean ativo;

    private LocalDate dataInicio;

    private LocalDate dataFim;
}
