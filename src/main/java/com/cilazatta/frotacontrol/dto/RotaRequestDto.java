package com.cilazatta.frotacontrol.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class RotaRequestDto {

    private String descricao;

    private Boolean ativa;

    private List<RotaFuncionarioRequestDto> passageiros =
            new ArrayList<>();
}