package com.cilazatta.frotacontrol.mapper;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.cilazatta.frotacontrol.dto.ViagemResponseDto;
import com.cilazatta.frotacontrol.entity.Viagem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ViagemMapper {

	private final ViagemParticipanteMapper participanteMapper;

	public ViagemResponseDto toResponse(Viagem entity) {

		ViagemResponseDto dto = new ViagemResponseDto();

		dto.setId(entity.getId());

		// Veículo
		dto.setVeiculoId(entity.getVeiculo().getId());

		dto.setPlaca(entity.getVeiculo().getPlaca());

		// Motorista
		dto.setMotoristaId(entity.getMotorista().getId());

		dto.setMotorista(entity.getMotorista().getNome());

		// Rota
		if (entity.getRota() != null) {

			dto.setRotaId(entity.getRota().getId());

			dto.setRota(entity.getRota().getDescricao());
		}

		dto.setTipoViagem(entity.getTipoViagem());

		dto.setFinalidade(entity.getFinalidade());

		dto.setKmInicial(entity.getKmInicial());

		dto.setKmFinal(entity.getKmFinal());

		dto.setStatus(entity.getStatus());

		dto.setDataHoraSaida(entity.getDataHoraSaida());

		dto.setDataHoraChegada(entity.getDataHoraChegada());

		if (entity.getPassageiros() != null) {

			dto.setParticipantes(entity.getPassageiros().stream().map(participanteMapper::toResponse)
					.collect(Collectors.toList()));
		} else {

			dto.setParticipantes(Collections.emptyList());
		}

		return dto;
	}
}
