package com.cilazatta.frotacontrol.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RotaResponseDto {

    private Long id;

    private String descricao;

    private Boolean ativa;

    private List<RotaFuncionarioResponseDto> passageiros =
            new ArrayList<>();
}