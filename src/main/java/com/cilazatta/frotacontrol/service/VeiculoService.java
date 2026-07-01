package com.cilazatta.frotacontrol.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cilazatta.frotacontrol.dto.VeiculoRequestDto;
import com.cilazatta.frotacontrol.dto.VeiculoResponseDto;
import com.cilazatta.frotacontrol.entity.Empresa;
import com.cilazatta.frotacontrol.entity.Veiculo;
import com.cilazatta.frotacontrol.enums.Role;
import com.cilazatta.frotacontrol.exeptions.AccessDeniedException;
import com.cilazatta.frotacontrol.exeptions.BusinessException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.VeiculoMapper;
import com.cilazatta.frotacontrol.repository.EmpresaRepository;
import com.cilazatta.frotacontrol.repository.VeiculoRepository;
import com.cilazatta.frotacontrol.security.service.UsuarioLogadoService;

@Service
public class VeiculoService {

	private final VeiculoRepository repository;
	private final EmpresaRepository empresaRepository;
	private final UsuarioLogadoService userLogado;
	private final VeiculoMapper mapper;

	public VeiculoService(VeiculoRepository repository,
			UsuarioLogadoService userLogado,
			EmpresaRepository empresaRepository, VeiculoMapper mapper) {
		super();
		this.repository = repository;
		this.empresaRepository = empresaRepository;
		this.userLogado = userLogado;
		this.mapper = mapper;
	}

	public VeiculoResponseDto salvar(VeiculoRequestDto request) {
		
		if (!userLogado.hasRole(Role.ROLE_GESTOR_ROTA)) {
		    throw new AccessDeniedException(
		        "Acesso negado. Apenas Gestor de Rota pode realizar esta operação."
		    );
		}
		
		Empresa empresa = empresaRepository.findById(userLogado.getEmpresaId())
				.orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));


		validarPlaca(request.getPlaca(), userLogado.getEmpresaId());

		Veiculo veiculo = mapper.toEntity(request);
		
		veiculo.setEmpresa(empresa);

		veiculo = repository.save(veiculo);

		return mapper.toResponse(veiculo);
	}

	public VeiculoResponseDto atualizar(Long id, VeiculoRequestDto request) {

		Veiculo veiculo = buscarEntidade(id);

		mapper.updateEntity(veiculo, request);

		veiculo = repository.save(veiculo);

		return mapper.toResponse(veiculo);
	}

	public VeiculoResponseDto buscarPorId(Long id) {

	    Long empresaId = userLogado.getEmpresaId();

	    Veiculo veiculo = repository
	            .findByIdAndEmpresaId(id, empresaId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Veículo não encontrado para a empresa do usuário"));

	    return mapper.toResponse(veiculo);
	}

	public List<VeiculoResponseDto> listar() {
		Long empresaId = userLogado.getEmpresaId(); 
		return repository.findByEmpresaId(empresaId).stream().map(mapper::toResponse).toList();
	}

	public void excluir(Long id) {

		Veiculo veiculo = buscarEntidade(id);

		veiculo.setAtivo(false);

		repository.save(veiculo);
	}
	public void excluir(Long id, Long empresaId) {
		
		Veiculo veiculo = buscarEntidadeByEmpresa(id, empresaId);
		
		veiculo.setAtivo(false);
		
		repository.save(veiculo);
	}

	private Veiculo buscarEntidade(Long id) {

		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));
	}

	private Veiculo buscarEntidadeByEmpresa(Long id, Long empresaId) {
		
		return repository.findByIdAndEmpresaId(id, empresaId).orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));
	}

//	private void validarPlaca(String placa) {
//
//		if (repository.existsByPlaca(placa)) {
//
//			throw new BusinessException("Placa já cadastrada");
//		}
//	}

	private void validarPlaca(String placa, Long empresaId) {
		
		if (repository.existsByPlacaAndEmpresaId(placa, empresaId)) {
			
			throw new BusinessException("Placa já cadastrada");
		}
	}

}
