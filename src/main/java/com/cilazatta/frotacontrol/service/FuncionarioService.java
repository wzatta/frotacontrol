package com.cilazatta.frotacontrol.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cilazatta.frotacontrol.dto.FuncionarioRequestDto;
import com.cilazatta.frotacontrol.dto.FuncionarioRequestUpdateDto;
import com.cilazatta.frotacontrol.dto.FuncionarioResponseDto;
import com.cilazatta.frotacontrol.entity.Empresa;
import com.cilazatta.frotacontrol.entity.Funcionario;
import com.cilazatta.frotacontrol.enums.Role;
import com.cilazatta.frotacontrol.exeptions.BusinessException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.FuncionarioMapper;
import com.cilazatta.frotacontrol.repository.EmpresaRepository;
import com.cilazatta.frotacontrol.repository.FuncionarioRepository;
import com.cilazatta.frotacontrol.security.service.UsuarioLogadoService;

@Service
public class FuncionarioService {

	private final FuncionarioRepository repository;
	private final EmpresaRepository empresaRepository;
	private final FuncionarioMapper mapper;
	private final UsuarioLogadoService userLogado;

	public FuncionarioService(FuncionarioRepository repository, 
			UsuarioLogadoService userLogado,
			EmpresaRepository empresaRepository,
			FuncionarioMapper mapper) {
		super();
		this.repository = repository;
		this.empresaRepository = empresaRepository;
		this.mapper = mapper;
		this.userLogado = userLogado;
	}

	public FuncionarioResponseDto salvar(FuncionarioRequestDto request) {

		Empresa empresa = this.verificarEmpresa(request);
		
//		Empresa empresa = empresaRepository.findById(request.getEmpresaId())
//				.orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

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
		
		Long empresaId = userLogado.getEmpresaId();

		  return repository.findByEmpresaIdOrderByNomeAsc(empresaId)
		            .stream()
		            .map(mapper::toResponse)
		            .toList();
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
	
	private Empresa verificarEmpresa(FuncionarioRequestDto dto) {

	    List<Role> roles = userLogado.getRoles();

	    boolean isAdmin = roles.contains(Role.ROLE_ADMIN);
	    boolean isAdminEmpresa = roles.contains(Role.ROLE_ADMIN_EMPRESA);

	    Long empresaId;

	    if (isAdmin) {
	        empresaId = dto.getEmpresaId();

	        if (empresaId == null) {
	            throw new RuntimeException("ADMIN deve informar empresaId");
	        }

	    } else if (isAdminEmpresa) {
	        empresaId = userLogado.getEmpresaId();

	    } else {
	        throw new RuntimeException("Usuário sem permissão para definir empresa");
	    }

	    return empresaRepository.findById(empresaId)
	            .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
	}

}
