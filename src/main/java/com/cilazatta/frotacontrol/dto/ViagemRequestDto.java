package com.cilazatta.frotacontrol.dto;

import java.util.ArrayList;
import java.util.List;

import com.cilazatta.frotacontrol.enums.TipoViagem;

import lombok.Data;

@Data
public class ViagemRequestDto {

    private Long veiculoId;

    private Long motoristaId;

    private Long rotaId;

    private TipoViagem tipoViagem;

    private String finalidade;

    private String origem;

    private String destino;

    private String latitudeInicio;

    private String longitudeInicio;

    private Long kmInicial;

    private String observacao;

    private List<ViagemParticipanteRequestDto>
            participantes = new ArrayList<>();
}