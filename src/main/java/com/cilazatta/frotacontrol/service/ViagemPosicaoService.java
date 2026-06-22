package com.cilazatta.frotacontrol.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cilazatta.frotacontrol.dto.ViagemPosicaoRequestDto;
import com.cilazatta.frotacontrol.dto.ViagemPosicaoResponseDto;
import com.cilazatta.frotacontrol.entity.Viagem;
import com.cilazatta.frotacontrol.entity.ViagemPosicao;
import com.cilazatta.frotacontrol.enums.StatusViagem;
import com.cilazatta.frotacontrol.exeptions.BusinessException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.ViagemPosicaoMapper;
import com.cilazatta.frotacontrol.repository.ViagemPosicaoRepository;
import com.cilazatta.frotacontrol.repository.ViagemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ViagemPosicaoService {

    private final ViagemRepository viagemRepository;

    private final ViagemPosicaoRepository viagemPosicaoRepository;

    private final ViagemPosicaoMapper mapper;

    public ViagemPosicaoResponseDto registrarPosicao(
            Long viagemId,
            ViagemPosicaoRequestDto request) {

        Viagem viagem =
                viagemRepository.findById(viagemId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Viagem não encontrada"));

        if (!StatusViagem.ABERTA.equals(
                viagem.getStatus())) {

            throw new BusinessException(
                    "A viagem não está aberta");
        }

        ViagemPosicao posicao =
                new ViagemPosicao();

        posicao.setViagem(viagem);

        posicao.setLatitude(
                request.getLatitude());

        posicao.setLongitude(
                request.getLongitude());

        posicao.setVelocidade(
                request.getVelocidade());

        posicao.setDataHora(
                request.getDataHora() != null
                        ? request.getDataHora()
                        : LocalDateTime.now());

        posicao =
                viagemPosicaoRepository.save(
                        posicao);

        return mapper.toResponse(posicao);
    }

    @Transactional(readOnly = true)
    public List<ViagemPosicaoResponseDto>
    listarPorViagem(Long viagemId) {

        return viagemPosicaoRepository
                .findByViagemIdOrderByDataHora(
                        viagemId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
