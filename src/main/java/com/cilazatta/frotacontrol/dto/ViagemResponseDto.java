package com.cilazatta.frotacontrol.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.cilazatta.frotacontrol.enums.StatusViagem;
import com.cilazatta.frotacontrol.enums.TipoViagem;

import lombok.Data;

@Data
public class ViagemResponseDto {

    private Long id;

    private Long veiculoId;

    private String placa;

    private Long motoristaId;

    private String motorista;

    private Long rotaId;

    private String rota;

    private TipoViagem tipoViagem;

    private String finalidade;

    private Long kmInicial;

    private Long kmFinal;

    private StatusViagem status;

    private LocalDateTime dataHoraSaida;

    private LocalDateTime dataHoraChegada;

    private List<ViagemParticipanteResponseDto>
            participantes;
}