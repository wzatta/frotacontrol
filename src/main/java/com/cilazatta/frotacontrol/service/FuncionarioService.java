package com.cilazatta.frotacontrol.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cilazatta.frotacontrol.dto.FuncionarioRequestDto;
import com.cilazatta.frotacontrol.dto.FuncionarioRequestUpdateDto;
import com.cilazatta.frotacontrol.dto.FuncionarioResponseDto;
import com.cilazatta.frotacontrol.entity.Empresa;
import com.cilazatta.frotacontrol.entity.Funcionario;
import com.cilazatta.frotacontrol.exeptions.BusinessException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.FuncionarioMapper;
import com.cilazatta.frotacontrol.repository.EmpresaRepository;
import com.cilazatta.frotacontrol.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	private final FuncionarioRepository repository;
	private final EmpresaRepository empresaRepository;
	private final FuncionarioMapper mapper;

	public FuncionarioService(FuncionarioRepository repository, EmpresaRepository empresaRepository,
			FuncionarioMapper mapper) {
		super();
		this.repository = repository;
		this.empresaRepository = empresaRepository;
		this.mapper = mapper;
	}

	public FuncionarioResponseDto salvar(FuncionarioRequestDto request) {

		Empresa empresa = empresaRepository.findById(request.getEmpresaId())
				.orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

		validarMatricula(request.getMatricula());

		Funcionario funcionario = mapper.toEntity(request);

		funcionario.setEmpresa(empresa);
		
		funcionario = repository.save(funcionario);

		return mapper.toResponse(funcionario);
	}

	public FuncionarioResponseDto atualizar(Long id, FuncionarioRequestUpdateDto request) {

		Funcionario funcionario = buscarEntidade(id);

		mapper.updateEntity(funcionario, request);

		funcionario = repository.save(funcionario);

		return mapper.toResponse(funcionario);
	}

	public FuncionarioResponseDto buscarPorId(Long id) {

		return mapper.toResponse(buscarEntidade(id));
	}

	public List<FuncionarioResponseDto> listar() {

		return repository.findAll().stream().map(mapper::toResponse).toList();
	}

	public void excluir(Long id) {

		Funcionario funcionario = buscarEntidade(id);

		funcionario.setAtivo(false);

		repository.save(funcionario);
	}

	private Funcionario buscarEntidade(Long id) {

		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));
	}

	private void validarMatricula(String matricula) {

		if (repository.existsByMatricula(matricula)) {
			throw new BusinessException("Matrícula já cadastrada");
		}
	}

}
