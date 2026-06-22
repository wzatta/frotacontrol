package com.cilazatta.frotacontrol.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cilazatta.frotacontrol.dto.VeiculoRequestDto;
import com.cilazatta.frotacontrol.dto.VeiculoResponseDto;
import com.cilazatta.frotacontrol.entity.Veiculo;
import com.cilazatta.frotacontrol.exeptions.BusinessException;
import com.cilazatta.frotacontrol.exeptions.ResourceNotFoundException;
import com.cilazatta.frotacontrol.mapper.VeiculoMapper;
import com.cilazatta.frotacontrol.repository.VeiculoRepository;

@Service
public class VeiculoService {

	  private final VeiculoRepository repository;
	    private final VeiculoMapper mapper;

	    public VeiculoService(VeiculoRepository repository, VeiculoMapper mapper) {
			super();
			this.repository = repository;
			this.mapper = mapper;
		}

		public VeiculoResponseDto salvar(
	            VeiculoRequestDto request) {

	        validarPlaca(request.getPlaca());

	        Veiculo veiculo =
	                mapper.toEntity(request);

	        veiculo =
	                repository.save(veiculo);

	        return mapper.toResponse(veiculo);
	    }

	    public VeiculoResponseDto atualizar(
	            Long id,
	            VeiculoRequestDto request) {

	        Veiculo veiculo =
	                buscarEntidade(id);

	        mapper.updateEntity(veiculo, request);

	        veiculo =
	                repository.save(veiculo);

	        return mapper.toResponse(veiculo);
	    }

	    public VeiculoResponseDto buscarPorId(Long id) {

	        return mapper.toResponse(
	                buscarEntidade(id)
	        );
	    }

	    public List<VeiculoResponseDto> listar() {

	        return repository.findAll()
	                .stream()
	                .map(mapper::toResponse)
	                .toList();
	    }

	    public void excluir(Long id) {

	        Veiculo veiculo =
	                buscarEntidade(id);

	        veiculo.setAtivo(false);

	        repository.save(veiculo);
	    }

	    private Veiculo buscarEntidade(Long id) {

	        return repository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException(
	                                "Veículo não encontrado"));
	    }

	    private void validarPlaca(String placa) {

	        if (repository.existsByPlaca(placa)) {

	            throw new BusinessException(
	                    "Placa já cadastrada");
	        }
	    }
	
	
}
