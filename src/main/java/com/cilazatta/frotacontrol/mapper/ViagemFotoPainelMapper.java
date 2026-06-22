package com.cilazatta.frotacontrol.mapper;

import org.springframework.stereotype.Component;

import com.cilazatta.frotacontrol.dto.ViagemFotoPainelResponseDto;
import com.cilazatta.frotacontrol.entity.ViagemFotoPainel;

@Component
public class ViagemFotoPainelMapper {

	public ViagemFotoPainelResponseDto toResponse(ViagemFotoPainel entity) {

		ViagemFotoPainelResponseDto dto = new ViagemFotoPainelResponseDto();

		dto.setId(entity.getId());

		dto.setUrlArquivo(entity.getUrlArquivo());

		dto.setKmLido(entity.getKmLido());

		dto.setValidado(entity.getValidado());

		dto.setTipoFoto(entity.getTipoFoto());

		dto.setJustificativa(entity.getJustificativa());

		dto.setDataHora(entity.getDataHora());

		return dto;
	}
}