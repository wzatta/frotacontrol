package com.cilazatta.frotacontrol.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cilazatta.frotacontrol.dto.ViagemAbertaResponseDto;
import com.cilazatta.frotacontrol.dto.ViagemAbrirRequestDto;
import com.cilazatta.frotacontrol.dto.ViagemEncerrarRequestDto;
import com.cilazatta.frotacontrol.dto.ViagemRequestDto;
import com.cilazatta.frotacontrol.dto.ViagemResponseDto;
import com.cilazatta.frotacontrol.entity.Empresa;
import com.cilazatta.frotacontrol.entity.Funcionario;
import com.cilazatta.frotacontrol.entity.Rota;
import com.cilazatta.frotacontrol.entity.RotaFuncionario;
import com.cilazatta.frotacontrol.entity.Veiculo;
import com.cilazatta.frotacontrol.entity.Viagem;
import com.cilazatta.frotacontrol.entity.ViagemParticipante;
import com.cilazatta.frotacontrol.enums.StatusViagem;
import com.cilazatta.frotacontrol.enums.TipoViagem;
import com.cilazatta.frotacontrol.enums.VeiculoStatus;
import com.cilazatta.frotacontrol.exeptions.BusinessException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.ViagemMapper;
import com.cilazatta.frotacontrol.repository.EmpresaRepository;
import com.cilazatta.frotacontrol.repository.FuncionarioRepository;
import com.cilazatta.frotacontrol.repository.RotaFuncionarioRepository;
import com.cilazatta.frotacontrol.repository.RotaRepository;
import com.cilazatta.frotacontrol.repository.VeiculoCondutorRepository;
import com.cilazatta.frotacontrol.repository.VeiculoRepository;
import com.cilazatta.frotacontrol.repository.ViagemParticipanteRepository;
import com.cilazatta.frotacontrol.repository.ViagemRepository;
import com.cilazatta.frotacontrol.security.service.UsuarioLogadoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
	private final UsuarioLogadoService userLogado;
	
	private final EmpresaRepository empresaRepository;
	
	private final GeolocalizacaoService geoLocService;

	public ViagemResponseDto abrirViagem(ViagemRequestDto request) {

		Veiculo veiculo = buscarVeiculo(request.getVeiculoId());

		Funcionario motorista = buscarFuncionario(request.getMotoristaId());

		validarMotoristaAutorizado(veiculo.getId(), motorista.getId());

		validarViagemAberta(veiculo.getId());

		validarKmInicial(veiculo, request.getKmInicial());

		Viagem viagem = new Viagem();

		viagem.setVeiculo(veiculo);

		viagem.setMotorista(motorista);

		viagem.setTipoViagem(request.getTipoViagem());

		viagem.setLatitude_inicio(request.getLatitudeInicio());

		viagem.setLongitude_inicio(request.getLongitudeInicio());

		viagem.setKmInicial(request.getKmInicial());

		viagem.setDataHoraAbertura(LocalDateTime.now());

		viagem.setStatus(StatusViagem.ABERTA);

		viagem.setObservacao(request.getObservacao());

		if (request.getRotaId() != null) {

			Rota rota = rotaRepository.findById(request.getRotaId())
					.orElseThrow(() -> new ResourceNotFoundException("Rota não encontrada"));

			viagem.setRota(rota);
		}

		viagem = viagemRepository.save(viagem);

		carregarParticipantesDaRota(viagem, motorista);

		return viagemMapper.toResponse(viagem);
	}

	@Transactional(readOnly = true)
	public ViagemResponseDto buscarPorId(Long id) {

		return viagemMapper.toResponse(buscarViagem(id));
	}

	@Transactional(readOnly = true)
	public List<ViagemResponseDto> listar() {

		return viagemRepository.findAll().stream().map(viagemMapper::toResponse).toList();
	}

	public ViagemResponseDto encerrarViagem(Long viagemId, ViagemEncerrarRequestDto request) {

		Viagem viagem = buscarViagem(viagemId);

		if (!StatusViagem.ABERTA.equals(viagem.getStatus())) {

			throw new BusinessException("A viagem não está aberta.");
		}

		if (request.getKmFinal() < viagem.getKmInicial()) {

			throw new BusinessException("Km final não pode ser menor que o km inicial.");
		}

		viagem.setKmFinal(request.getKmFinal());

		viagem.setLatitude_fim(request.getLatitudeFim());

		viagem.setLongitude_fim(request.getLongitudeFim());

		viagem.setDataHoraEncerramento(LocalDateTime.now());

		viagem.setObservacao(request.getObservacao());

		viagem.setStatus(StatusViagem.FINALIZADA);

		Veiculo veiculo = viagem.getVeiculo();

		veiculo.setKmAtual(request.getKmFinal());

		veiculoRepository.save(veiculo);

		viagemRepository.save(viagem);

		return viagemMapper.toResponse(viagem);
	}
	
	
	
	
	
	//METODOS ACESSORIOS

	private Viagem buscarViagem(Long id) {
		return viagemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Viagem não encontrada."));
	}

	private Veiculo buscarVeiculo(Long id) {
		return veiculoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado."));
	}

	private Funcionario buscarFuncionario(Long id) {
		return funcionarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado."));
	}

	private void validarMotoristaAutorizado(Long veiculoId, Long funcionarioId) {
		boolean autorizado = veiculoCondutorRepository.existsByVeiculoIdAndFuncionarioId(veiculoId, funcionarioId);
		if (!autorizado) {
			throw new BusinessException("Funcionário não autorizado para dirigir este veículo.");
		}
	}

	private void validarViagemAberta(Long veiculoId) {
		viagemRepository.findByVeiculoIdAndStatus(veiculoId, StatusViagem.ABERTA).ifPresent(v -> {
			throw new BusinessException("Já existe uma viagem aberta para este veículo.");
		});
	}

	private void validarKmInicial(Veiculo veiculo, Long kmInformado) {
		if (!veiculo.getKmAtual().equals(kmInformado)) {
			throw new BusinessException("Km inicial divergente do km atual do veículo.");
		}
	}

	private void carregarParticipantesDaRota(Viagem viagem, Funcionario motorista) {
		if (viagem.getRota() == null) {
			return;
		}

		List<RotaFuncionario> passageiros = rotaFuncionarioRepository
				.findByRotaIdOrderByOrdem(viagem.getRota().getId());

		for (RotaFuncionario passageiro : passageiros) {

			if (passageiro.getFuncionario().getId().equals(motorista.getId())) {

				continue;
			}

			ViagemParticipante participante = new ViagemParticipante();

			participante.setViagem(viagem);

			participante.setFuncionario(passageiro.getFuncionario());

			participante.setPresente(false);

			viagemParticipanteRepository.save(participante);
		}
	}

	@Transactional
    public ViagemAbertaResponseDto reservarViagem(ViagemAbrirRequestDto dto) {
        // 1. Recupera as entidades obrigatórias do banco através dos IDs
        Veiculo veiculo = buscarVeiculo(dto.getVeiculoId());

        // 2. SIMULAÇÃO: Aqui você pegaria os IDs do usuário autenticado no sistema (ex: SecurityContextHolder)
        Long idMotoristaLogado = userLogado.getUsuario().getFuncionario().getId(); 
        Long idEmpresaUsuarioLogado = userLogado.getEmpresaId();

        Funcionario motorista = buscarFuncionario(idMotoristaLogado);
        
     // 3. Modifica o status do veículo diretamente (O Dirty Checking do @Transactional vai salvar sozinho)
        veiculo.setStatus(VeiculoStatus.RESERVADO);

        Empresa empresa = empresaRepository.findById(idEmpresaUsuarioLogado)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada"));

        // 3. Instancia a Viagem usando o construtor de negócio que criamos na etapa anterior
        Viagem viagem = new Viagem(veiculo, motorista, empresa);

        viagem.setStatus(StatusViagem.ABERTA);
        
        viagem.setTipoViagem(dto.getTipoViagem()!= null ? dto.getTipoViagem() : TipoViagem.SERVICO_INTERNO);
        
        viagem.setKmInicial(dto.getKmInicial() != null ? dto.getKmInicial() : veiculo.getKmAtual());
        
        viagem.setLatitude_inicio(dto.getLatitudeInicio() != null ? dto.getLatitudeInicio() : BigDecimal.ZERO);;
        viagem.setLongitude_inicio(dto.getLongitudeInicio() != null ? dto.getLongitudeInicio() : BigDecimal.ZERO); 
        
        
        String endereco = geoLocService.buscarEnderecoPorCoordenadas(
                viagem.getLatitude_inicio(), 
                viagem.getLongitude_inicio());

       viagem.setLocalPartida(endereco);
        
        viagem.setDataHoraAbertura(LocalDateTime.now());
        
        // 5. Salva no banco de dados
       Viagem viagemAberta = viagemRepository.save(viagem);

       
       
        
        return new ViagemAbertaResponseDto(viagemAberta.getId());
                
        
    }

	public ViagemResponseDto cancelarViagem(Long id, @Valid ViagemEncerrarRequestDto request) {
		// TODO Auto-generated method stub
		return null;
	}
}

