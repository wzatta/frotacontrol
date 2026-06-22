package com.cilazatta.frotacontrol.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cilazatta.frotacontrol.dto.ViagemFotoPainelRequestDto;
import com.cilazatta.frotacontrol.dto.ViagemFotoPainelResponseDto;
import com.cilazatta.frotacontrol.entity.Viagem;
import com.cilazatta.frotacontrol.entity.ViagemFotoPainel;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.ViagemFotoPainelMapper;
import com.cilazatta.frotacontrol.repository.ViagemFotoPainelRepository;
import com.cilazatta.frotacontrol.repository.ViagemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ViagemFotoPainelService {

	private final ViagemRepository viagemRepository;

	private final ViagemFotoPainelRepository repository;

	private final ViagemFotoPainelMapper mapper;

	public ViagemFotoPainelResponseDto registrarFoto(Long viagemId, ViagemFotoPainelRequestDto request) {

		Viagem viagem = viagemRepository.findById(viagemId)
				.orElseThrow(() -> new ResourceNotFoundException("Viagem não encontrada"));

		ViagemFotoPainel foto = new ViagemFotoPainel();

		foto.setViagem(viagem);

		foto.setUrlArquivo(request.getUrlArquivo());

		foto.setKmLido(request.getKmLido());

		foto.setTipoFoto(request.getTipoFoto());

		foto.setJustificativa(request.getJustificativa());

		foto.setValidado(false);

		foto.setDataHora(LocalDateTime.now());

		foto = repository.save(foto);

		return mapper.toResponse(foto);
	}

	@Transactional
	public List<ViagemFotoPainelResponseDto> listarPorViagem(Long viagemId) {

		return repository.findByViagemId(viagemId).stream().map(mapper::toResponse).toList();
	}
}