package com.cilazatta.frotacontrol.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.cilazatta.frotacontrol.dto.RotaFuncionarioRequestDto;
import com.cilazatta.frotacontrol.dto.RotaFuncionarioResponseDto;
import com.cilazatta.frotacontrol.entity.Funcionario;
import com.cilazatta.frotacontrol.entity.Rota;
import com.cilazatta.frotacontrol.entity.RotaFuncionario;
import com.cilazatta.frotacontrol.exeptions.AccessDeniedException;
import com.cilazatta.frotacontrol.exeptions.BusinessException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.RotaFuncionarioMapper;
import com.cilazatta.frotacontrol.repository.FuncionarioRepository;
import com.cilazatta.frotacontrol.repository.RotaFuncionarioRepository;
import com.cilazatta.frotacontrol.repository.RotaRepository;
import com.cilazatta.frotacontrol.security.service.UsuarioLogadoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RotaFuncionarioService {

    private final RotaFuncionarioRepository repository;
    private final RotaRepository rotaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final RotaFuncionarioMapper mapper;
    private final UsuarioLogadoService userLogado;

    @Transactional
    public RotaFuncionarioResponseDto adicionar(Long rotaId, RotaFuncionarioRequestDto dto) {

        Long empresaId = userLogado.getEmpresaId();

        Rota rota = rotaRepository.findByIdAndEmpresaId(rotaId, empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada"));

        Funcionario funcionario = funcionarioRepository
                .findByIdAndEmpresaId(dto.getFuncionarioId(), empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));

        RotaFuncionario entity = new RotaFuncionario();

        entity.setRota(rota);
        entity.setFuncionario(funcionario);
        entity.setOrdem(dto.getOrdem());
        entity.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);

        this.validarFuncionarioEmOutraRota(rotaId, empresaId);
        
        return mapper.toResponse(repository.save(entity));
    }

    public List<RotaFuncionarioResponseDto> listarPorRota(Long rotaId) {

        Long empresaId = userLogado.getEmpresaId();

        Rota rota = rotaRepository.findByIdAndEmpresaId(rotaId, empresaId)
                .orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada"));

        return rota.getPassageiros()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional
    public void remover(Long id) {

        Long empresaId = userLogado.getEmpresaId();

        RotaFuncionario entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado"));

        if (!entity.getRota().getEmpresa().getId().equals(empresaId)) {
            throw new AccessDeniedException("Acesso negado");
        }

        repository.delete(entity);
    }
    
    private void validarFuncionarioEmOutraRota(Long funcionarioId, Long empresaId) {

    	 boolean jaExiste = repository
    	            .existsByFuncionarioIdAndRotaAtivaTrueAndRotaEmpresaId(funcionarioId, empresaId);

        if (jaExiste) {
            throw new BusinessException(
                "Funcionário já está vinculado a outra rota ativa"
            );
        }
    }
}