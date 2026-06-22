package com.cilazatta.frotacontrol.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cilazatta.frotacontrol.dto.ViagemEncerrarRequestDto;
import com.cilazatta.frotacontrol.dto.ViagemRequestDto;
import com.cilazatta.frotacontrol.dto.ViagemResponseDto;
import com.cilazatta.frotacontrol.entity.Funcionario;
import com.cilazatta.frotacontrol.entity.Rota;
import com.cilazatta.frotacontrol.entity.RotaFuncionario;
import com.cilazatta.frotacontrol.entity.Veiculo;
import com.cilazatta.frotacontrol.entity.Viagem;
import com.cilazatta.frotacontrol.entity.ViagemParticipante;
import com.cilazatta.frotacontrol.enums.StatusViagem;
import com.cilazatta.frotacontrol.exeptions.BusinessException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.ViagemMapper;
import com.cilazatta.frotacontrol.repository.FuncionarioRepository;
import com.cilazatta.frotacontrol.repository.RotaFuncionarioRepository;
import com.cilazatta.frotacontrol.repository.RotaRepository;
import com.cilazatta.frotacontrol.repository.VeiculoCondutorRepository;
import com.cilazatta.frotacontrol.repository.VeiculoRepository;
import com.cilazatta.frotacontrol.repository.ViagemParticipanteRepository;
import com.cilazatta.frotacontrol.repository.ViagemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ViagemService {

    private final ViagemRepository viagemRepository;

    private final VeiculoRepository veiculoRepository;

    private final FuncionarioRepository funcionarioRepository;

    private final VeiculoCondutorRepository veiculoCondutorRepository;

    private final RotaRepository rotaRepository;

    private final RotaFuncionarioRepository rotaFuncionarioRepository;

    private final ViagemParticipanteRepository viagemParticipanteRepository;

    private final ViagemMapper viagemMapper;

    public ViagemResponseDto abrirViagem(ViagemRequestDto request) {

        Veiculo veiculo = buscarVeiculo(request.getVeiculoId());

        Funcionario motorista = buscarFuncionario(
                request.getMotoristaId());

        validarMotoristaAutorizado(
                veiculo.getId(),
                motorista.getId());

        validarViagemAberta(
                veiculo.getId());

        validarKmInicial(
                veiculo,
                request.getKmInicial());

        Viagem viagem = new Viagem();

        viagem.setVeiculo(veiculo);

        viagem.setMotorista(motorista);

        viagem.setTipoViagem(
                request.getTipoViagem());

        viagem.setFinalidade(
                request.getFinalidade());

        viagem.setOrigem(
                request.getOrigem());

        viagem.setDestino(
                request.getDestino());

        viagem.setLatitude_inicio(
                request.getLatitudeInicio());

        viagem.setLongitude_inicio(
                request.getLongitudeInicio());

        viagem.setKmInicial(
                request.getKmInicial());

        viagem.setDataHoraSaida(
                LocalDateTime.now());

        viagem.setStatus(
                StatusViagem.ABERTA);

        viagem.setObservacao(
                request.getObservacao());

        if (request.getRotaId() != null) {

            Rota rota = rotaRepository.findById(
                    request.getRotaId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Rota não encontrada"));

            viagem.setRota(rota);
        }

        viagem = viagemRepository.save(viagem);

        carregarParticipantesDaRota(
                viagem,
                motorista);

        return viagemMapper.toResponse(
                viagem);
    }

    @Transactional(readOnly = true)
    public ViagemResponseDto buscarPorId(Long id) {

        return viagemMapper.toResponse(
                buscarViagem(id));
    }

    @Transactional(readOnly = true)
    public List<ViagemResponseDto> listar() {

        return viagemRepository.findAll()
                .stream()
                .map(viagemMapper::toResponse)
                .toList();
    }


    public ViagemResponseDto encerrarViagem(
            Long viagemId,
            ViagemEncerrarRequestDto request) {

        Viagem viagem = buscarViagem(
                viagemId);

        if (!StatusViagem.ABERTA.equals(
                viagem.getStatus())) {

            throw new BusinessException(
                    "A viagem não está aberta.");
        }

        if (request.getKmFinal()
                < viagem.getKmInicial()) {

            throw new BusinessException(
                    "Km final não pode ser menor que o km inicial.");
        }

        viagem.setKmFinal(
                request.getKmFinal());

        viagem.setLatitude_fim(
                String.valueOf(
                        request.getLatitudeFim()));

        viagem.setLongitude_fim(
                String.valueOf(
                        request.getLongitudeFim()));

        viagem.setDataHoraChegada(
                LocalDateTime.now());

        viagem.setObservacao(
                request.getObservacao());

        viagem.setStatus(
                StatusViagem.FINALIZADA);

        Veiculo veiculo =
                viagem.getVeiculo();

        veiculo.setKmAtual(
                request.getKmFinal());

        veiculoRepository.save(
                veiculo);

        viagemRepository.save(
                viagem);

        return viagemMapper.toResponse(
                viagem);
    }

    private Viagem buscarViagem(
            Long id) {

        return viagemRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Viagem não encontrada."));
    }

    private Veiculo buscarVeiculo(
            Long id) {

        return veiculoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Veículo não encontrado."));
    }

    private Funcionario buscarFuncionario(
            Long id) {

        return funcionarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Funcionário não encontrado."));
    }

    private void validarMotoristaAutorizado(
            Long veiculoId,
            Long funcionarioId) {

        boolean autorizado =
                veiculoCondutorRepository
                        .existsByVeiculoIdAndFuncionarioId(
                                veiculoId,
                                funcionarioId);

        if (!autorizado) {

            throw new BusinessException(
                    "Funcionário não autorizado para dirigir este veículo.");
        }
    }

    private void validarViagemAberta(
            Long veiculoId) {

        viagemRepository
                .findByVeiculoIdAndStatus(
                        veiculoId,
                        StatusViagem.ABERTA)
                .ifPresent(v -> {

                    throw new BusinessException(
                            "Já existe uma viagem aberta para este veículo.");
                });
    }

    private void validarKmInicial(
            Veiculo veiculo,
            Long kmInformado) {

        if (!veiculo.getKmAtual()
                .equals(kmInformado)) {

            throw new BusinessException(
                    "Km inicial divergente do km atual do veículo.");
        }
    }

    private void carregarParticipantesDaRota(
            Viagem viagem,
            Funcionario motorista) {

        if (viagem.getRota() == null) {
            return;
        }

        List<RotaFuncionario> passageiros =
                rotaFuncionarioRepository
                        .findByRotaIdOrderByOrdem(
                                viagem.getRota().getId());

        for (RotaFuncionario passageiro : passageiros) {

            if (passageiro.getFuncionario()
                    .getId()
                    .equals(motorista.getId())) {

                continue;
            }

            ViagemParticipante participante =
                    new ViagemParticipante();

            participante.setViagem(
                    viagem);

            participante.setFuncionario(
                    passageiro.getFuncionario());

            participante.setPresente(
                    false);

            viagemParticipanteRepository
                    .save(participante);
        }
    }
}

