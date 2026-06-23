package com.cilazatta.frotacontrol.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cilazatta.frotacontrol.dto.VeiculoCondutorRequestDto;
import com.cilazatta.frotacontrol.dto.VeiculoCondutorResponseDto;
import com.cilazatta.frotacontrol.entity.Funcionario;
import com.cilazatta.frotacontrol.entity.Veiculo;
import com.cilazatta.frotacontrol.entity.VeiculoCondutor;
import com.cilazatta.frotacontrol.exeptions.BusinessException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.VeiculoCondutorMapper;
import com.cilazatta.frotacontrol.repository.FuncionarioRepository;
import com.cilazatta.frotacontrol.repository.VeiculoCondutorRepository;
import com.cilazatta.frotacontrol.repository.VeiculoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VeiculoCondutorService {

	private final VeiculoCondutorRepository repository;
	private final VeiculoRepository veiculoRepository;
	private final FuncionarioRepository funcionarioRepository;

	private final VeiculoCondutorMapper mapper;

	public VeiculoCondutorResponseDto salvar(VeiculoCondutorRequestDto request) {

		Veiculo veiculo = veiculoRepository.findById(request.getVeiculoId())
				.orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

		Funcionario funcionario = funcionarioRepository.findById(request.getFuncionarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));

		repository.findByVeiculoIdAndFuncionarioId(request.getVeiculoId(), request.getFuncionarioId()).ifPresent(v -> {
			throw new BusinessException("Funcionário já autorizado para este veículo");
		});

		if (!Boolean.TRUE.equals(request.getPrincipal())) {

			throw new BusinessException("Funcionário Não Habilitado");
			
//			List<VeiculoCondutor> condutores = repository.findByVeiculoIdAndAtivoTrue(veiculo.getId());
//
//			condutores.forEach(c -> {
//				c.setPrincipal(false);
//				repository.save(c);
//			});
		}

		long contador = this.repository.countByVeiculoIdAndAtivoTrue(request.getVeiculoId());
		
		if(contador >= 1) {
			throw new BusinessException("Limite de Motorista Excedido para o Veiculo");
			
		}
		
		VeiculoCondutor entity = new VeiculoCondutor();

		entity.setVeiculo(veiculo);
		entity.setFuncionario(funcionario);

		entity.setPrincipal(request.getPrincipal() != null ? request.getPrincipal() : false);

		entity.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);

		entity.setDataInicio(request.getDataInicio());
		entity.setDataFim(request.getDataFim());

		repository.save(entity);

		return mapper.toResponse(entity);
	}

	public VeiculoCondutorResponseDto atualizar(Long id, VeiculoCondutorRequestDto request) {

		VeiculoCondutor entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Condutor não encontrado"));

		if (Boolean.TRUE.equals(request.getPrincipal())) {

			List<VeiculoCondutor> condutores = repository.findByVeiculoIdAndAtivoTrue(entity.getVeiculo().getId());

			condutores.stream().filter(c -> !c.getId().equals(id)).forEach(c -> {
				c.setPrincipal(false);
				repository.save(c);
			});
		}

		mapper.updateEntity(entity, request);

		repository.save(entity);

		return mapper.toResponse(entity);
	}

	@Transactional
	public VeiculoCondutorResponseDto buscarPorId(Long id) {

		VeiculoCondutor entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Condutor não encontrado"));

		return mapper.toResponse(entity);
	}

	@Transactional
	public List<VeiculoCondutorResponseDto> listarPorVeiculo(Long veiculoId) {

		return repository.findByVeiculoIdAndAtivoTrue(veiculoId).stream().map(mapper::toResponse).toList();
	}

	public void excluir(Long id) {

		VeiculoCondutor entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Condutor não encontrado"));

		entity.setAtivo(false);

		repository.save(entity);
	}

}
