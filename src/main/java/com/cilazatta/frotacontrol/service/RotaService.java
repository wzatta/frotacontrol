package com.cilazatta.frotacontrol.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cilazatta.frotacontrol.dto.RotaFuncionarioRequestDto;
import com.cilazatta.frotacontrol.dto.RotaRequestDto;
import com.cilazatta.frotacontrol.dto.RotaResponseDto;
import com.cilazatta.frotacontrol.entity.Funcionario;
import com.cilazatta.frotacontrol.entity.Rota;
import com.cilazatta.frotacontrol.entity.RotaFuncionario;
import com.cilazatta.frotacontrol.exeptions.BusinessException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.RotaMapper;
import com.cilazatta.frotacontrol.repository.FuncionarioRepository;
import com.cilazatta.frotacontrol.repository.RotaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RotaService  {

    private final RotaRepository rotaRepository;

    private final FuncionarioRepository funcionarioRepository;

    private final RotaMapper rotaMapper;

    public RotaResponseDto salvar(RotaRequestDto request) {

        if (rotaRepository.existsByDescricaoIgnoreCase(
                request.getDescricao())) {

            throw new BusinessException(
                    "Já existe uma rota com esta descrição.");
        }

        Rota rota = new Rota();

        rota.setDescricao(request.getDescricao());

        rota.setAtiva(
                request.getAtiva() == null
                        ? true
                        : request.getAtiva());

        adicionarPassageiros(rota,
                request.getPassageiros());

        rota = rotaRepository.save(rota);

        return rotaMapper.toResponse(rota);
    }

    public RotaResponseDto atualizar(
            Long id,
            RotaRequestDto request) {

        Rota rota = buscarEntidade(id);

        rota.setDescricao(request.getDescricao());

        rota.setAtiva(request.getAtiva());

        rota.getPassageiros().clear();

        adicionarPassageiros(
                rota,
                request.getPassageiros());

        rota = rotaRepository.save(rota);

        return rotaMapper.toResponse(rota);
    }

    @Transactional(readOnly = true)
    public RotaResponseDto buscarPorId(Long id) {

        return rotaMapper.toResponse(
                buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<RotaResponseDto> listar() {

        return rotaRepository.findAll()
                .stream()
                .map(rotaMapper::toResponse)
                .toList();
    }

    public void excluir(Long id) {

        Rota rota = buscarEntidade(id);

        rota.setAtiva(false);

        rotaRepository.save(rota);
    }

    private Rota buscarEntidade(Long id) {

        return rotaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Rota não encontrada."));
    }

    private void adicionarPassageiros(
            Rota rota,
            List<RotaFuncionarioRequestDto> passageiros) {

        if (passageiros == null) {
            return;
        }

        for (RotaFuncionarioRequestDto dto : passageiros) {

            Funcionario funcionario =
                    funcionarioRepository.findById(
                            dto.getFuncionarioId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Funcionário não encontrado: "
                                                    + dto.getFuncionarioId()));

            RotaFuncionario passageiro =
                    new RotaFuncionario();

            passageiro.setRota(rota);

            passageiro.setFuncionario(funcionario);

            passageiro.setOrdem(dto.getOrdem());

            passageiro.setAtivo(
                    dto.getAtivo() == null
                            ? true
                            : dto.getAtivo());

            rota.addPassageiro(passageiro);
        }
    }
}