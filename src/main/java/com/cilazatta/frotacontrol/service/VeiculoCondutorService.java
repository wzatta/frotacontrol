package com.cilazatta.frotacontrol.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cilazatta.frotacontrol.dto.VeiculoAutorizadoDto;
import com.cilazatta.frotacontrol.dto.VeiculoCondutorRequestDto;
import com.cilazatta.frotacontrol.dto.VeiculoCondutorResponseDto;
import com.cilazatta.frotacontrol.entity.Funcionario;
import com.cilazatta.frotacontrol.entity.Usuario;
import com.cilazatta.frotacontrol.entity.Veiculo;
import com.cilazatta.frotacontrol.entity.VeiculoCondutor;
import com.cilazatta.frotacontrol.enums.Role;
import com.cilazatta.frotacontrol.enums.VeiculoStatus;
import com.cilazatta.frotacontrol.exeptions.AccessDeniedException;
import com.cilazatta.frotacontrol.exeptions.BusinessException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.VeiculoCondutorMapper;
import com.cilazatta.frotacontrol.repository.FuncionarioRepository;
import com.cilazatta.frotacontrol.repository.UsuarioRepository;
import com.cilazatta.frotacontrol.repository.VeiculoCondutorRepository;
import com.cilazatta.frotacontrol.repository.VeiculoRepository;
import com.cilazatta.frotacontrol.security.service.UsuarioLogadoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VeiculoCondutorService {

	private final VeiculoCondutorRepository repository;
	private final VeiculoRepository veiculoRepository;
	private final UsuarioRepository usuarioRepository;
	private final UsuarioLogadoService userLogado;
	private final FuncionarioRepository funcionarioRepository;

	private final VeiculoCondutorMapper mapper;

	public VeiculoCondutorResponseDto salvar(VeiculoCondutorRequestDto request) {
		
		if (!userLogado.hasRole(Role.ROLE_GESTOR_ROTA)) {
		    throw new AccessDeniedException(
		        "Acesso negado. Apenas Gestor de Rota pode realizar esta operação."
		    );
		}
		
		Long empresaId = userLogado.getEmpresaId();

		Veiculo veiculo = veiculoRepository.findByIdAndEmpresaId(request.getVeiculoId(),empresaId)
				.orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado"));

		Funcionario funcionario = funcionarioRepository.findByIdAndEmpresaId(request.getFuncionarioId(),empresaId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));
		
		Usuario usuario = usuarioRepository.findByFuncionarioIdAndEmpresaId(funcionario.getId(), empresaId)
			    .orElseThrow(() -> 
			        new BusinessException("Funcionário não possui usuário cadastrado"));

			if (!usuario.getRoles().contains(Role.ROLE_MOTORISTA)) {
			    throw new BusinessException(
			        "Funcionário não possui perfil de motorista");
			}

		repository.findByVeiculoIdAndFuncionarioId(request.getVeiculoId(), request.getFuncionarioId()).ifPresent(v -> {
			throw new BusinessException("Funcionário já autorizado para este veículo");
		});

		if (!Boolean.TRUE.equals(request.getPrincipal())) {

			throw new BusinessException("Funcionário Não Habilitado");
			
		}

		if(repository.countByVeiculoIdAndAtivoTrue(
		        request.getVeiculoId()) >= 10) {

		    throw new BusinessException(
		            "Veículo já possui o limite máximo de condutores.");
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
	
	/**
     * Busca os veículos do banco e transforma em DTOs na camada de negócio.
     */
    @Transactional
    public List<VeiculoAutorizadoDto> listarVeiculosAutorizadosParaMotorista() {
    	
    	Long funcionarioId = userLogado.getUsuario().getFuncionario().getId();
    	Long empresaId = userLogado.getEmpresaId();
    	
        if (funcionarioId == null || empresaId == null) {
            throw new IllegalArgumentException("O ID do funcionário e o ID da empresa não podem ser nulos.");
        }

        List<Veiculo> veiculos = repository
                .findVeiculosAutorizadosPorStatus(funcionarioId, empresaId, VeiculoStatus.DISPONIVEL);

        return veiculos.stream()
                .map(v -> new VeiculoAutorizadoDto(
                        v.getId(),
                        v.getPlaca(),
                        v.getModelo(),
                        v.getTag(),
                        v.getKmAtual() // Preenche o hodometroAtual com o kmAtual da entidade
                ))
                .collect(Collectors.toList());
    }
	
	

}
