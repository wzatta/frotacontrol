package com.cilazatta.frotacontrol.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cilazatta.frotacontrol.dto.RotaRequestDto;
import com.cilazatta.frotacontrol.dto.RotaResponseDto;
import com.cilazatta.frotacontrol.entity.Empresa;
import com.cilazatta.frotacontrol.entity.Rota;
import com.cilazatta.frotacontrol.enums.Role;
import com.cilazatta.frotacontrol.exeptions.AccessDeniedException;
import com.cilazatta.frotacontrol.exeptions.BusinessException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.RotaMapper;
import com.cilazatta.frotacontrol.repository.EmpresaRepository;
import com.cilazatta.frotacontrol.repository.RotaRepository;
import com.cilazatta.frotacontrol.security.service.UsuarioLogadoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RotaService {

	private final RotaRepository rotaRepository;

	private final UsuarioLogadoService userLogado;

	private final EmpresaRepository empresaRepository;

	private final RotaMapper rotaMapper;

	public RotaResponseDto salvar(RotaRequestDto request) {
		
		validarResponsabilidadeDoUsuarioLogado();

		Long empresaId = userLogado.getEmpresaId();

		Empresa empresa = empresaRepository.findById(empresaId)
				.orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada"));

		Rota rota = new Rota();

		rota.setEmpresa(empresa);

		if (rotaRepository.existsByDescricaoIgnoreCaseAndEmpresaId(request.getDescricao(), empresaId)) {

			throw new BusinessException("Já existe uma rota com esta descrição.");
		}

		rota.setDescricao(request.getDescricao());

		rota.setAtiva(request.getAtiva() == null ? true : request.getAtiva());

		rota = rotaRepository.save(rota);

		return rotaMapper.toResponse(rota);
	}

	

	public RotaResponseDto atualizar(Long id, RotaRequestDto request) {
		
		validarResponsabilidadeDoUsuarioLogado();


		Rota rota = buscarEntidade(id);

		rota.setDescricao(request.getDescricao());

		rota.setAtiva(request.getAtiva());

		rota.getPassageiros().clear();

		rota = rotaRepository.save(rota);

		return rotaMapper.toResponse(rota);
	}

	@Transactional(readOnly = true)
	public RotaResponseDto buscarPorId(Long id) {

		validarResponsabilidadeDoUsuarioLogado();

		
		return rotaMapper.toResponse(buscarEntidade(id));
	}

	@Transactional(readOnly = true)
	public List<RotaResponseDto> listar() {
		
		validarResponsabilidadeDoUsuarioLogado();


		Long empresaId = userLogado.getEmpresaId();

		return rotaRepository.findByEmpresaIdOrderByDescricaoAsc(empresaId).stream().map(rotaMapper::toResponse)
				.toList();
	}

	public void excluir(Long id) {
		
		validarResponsabilidadeDoUsuarioLogado();


		Rota rota = buscarEntidade(id);

		rota.setAtiva(false);

		rotaRepository.save(rota);
	}

	private Rota buscarEntidade(Long id) {

		Long empresaId = userLogado.getEmpresaId();

		return rotaRepository.findByIdAndEmpresaId(id, empresaId)
				.orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada"));
	}
	
	private void validarResponsabilidadeDoUsuarioLogado() {
		if (!userLogado.hasRole(Role.ROLE_GESTOR_ROTA)) {
		    throw new AccessDeniedException(
		        "Acesso negado");
		}
		
	}

}