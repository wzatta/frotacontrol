package com.cilazatta.frotacontrol.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cilazatta.frotacontrol.dto.EmpresaRequestDto;
import com.cilazatta.frotacontrol.dto.EmpresaResponseDto;
import com.cilazatta.frotacontrol.entity.Empresa;
import com.cilazatta.frotacontrol.enums.Role;
import com.cilazatta.frotacontrol.exeptions.BusinessException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.EmpresaMapper;
import com.cilazatta.frotacontrol.repository.EmpresaRepository;
import com.cilazatta.frotacontrol.security.service.UsuarioLogadoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpresaService {

	private final EmpresaRepository repository;

	private final EmpresaMapper mapper;
	
	private final UsuarioLogadoService userLogado;

	public EmpresaResponseDto criar(EmpresaRequestDto request) {

		verificarRoleAdmin();
			
		
		if (repository.existsByCnpj(request.getCnpj())) {

			throw new BusinessException("Já existe empresa cadastrada com este CNPJ.");
		}

		Empresa empresa = mapper.toEntity(request);

		empresa = repository.save(empresa);

		return mapper.toResponse(empresa);
	}

	private void verificarRoleAdmin() {
	    List<Role> roles = userLogado.getRoles();

	    if (!roles.contains(Role.ROLE_ADMIN)) {
	        throw new RuntimeException("Apenas ADMIN tem acesso à empresa");
	    }
	}

	public EmpresaResponseDto atualizar(Long id, EmpresaRequestDto request) {

		verificarRoleAdmin();
		
		Empresa empresa = buscarEntity(id);

		if (!empresa.getCnpj().equals(request.getCnpj()) && repository.existsByCnpj(request.getCnpj())) {

			throw new BusinessException("Já existe empresa cadastrada com este CNPJ.");
		}

		mapper.updateEntity(empresa, request);

		empresa = repository.save(empresa);

		return mapper.toResponse(empresa);
	}

	@Transactional(readOnly = true)
	public EmpresaResponseDto buscarPorId(Long id) {

		verificarRoleAdmin();
		
		return mapper.toResponse(buscarEntity(id));
	}

	@Transactional(readOnly = true)
	public List<EmpresaResponseDto> listar() {

		verificarRoleAdmin();
		
		return repository.findAll().stream().map(mapper::toResponse).toList();
	}

	public void excluir(Long id) {

		verificarRoleAdmin();
		
		Empresa empresa = buscarEntity(id);

		repository.delete(empresa);
	}

	private Empresa buscarEntity(Long id) {

		verificarRoleAdmin();
		
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));
	}
}
