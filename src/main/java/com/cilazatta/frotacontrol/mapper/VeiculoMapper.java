package com.cilazatta.frotacontrol.mapper;

import org.springframework.stereotype.Component;

import com.cilazatta.frotacontrol.dto.VeiculoRequestDto;
import com.cilazatta.frotacontrol.dto.VeiculoResponseDto;
import com.cilazatta.frotacontrol.entity.Veiculo;

@Component
public class VeiculoMapper implements BaseMapper<Veiculo, VeiculoRequestDto, VeiculoResponseDto, VeiculoRequestDto> {

	public Veiculo toEntity(VeiculoRequestDto request) {

		Veiculo veiculo = new Veiculo();

		veiculo.setTag(request.getTag());
		veiculo.setPlaca(request.getPlaca());
		veiculo.setMarca(request.getMarca());
		veiculo.setModelo(request.getModelo());

		veiculo.setAno(request.getAno());

		veiculo.setCapacidadeTotal(request.getCapacidadeTotal());

		veiculo.setKmAtual(request.getKmAtual() == null ? 0L : request.getKmAtual());

		veiculo.setAtivo(request.getAtivo() == null ? true : request.getAtivo());

		return veiculo;
	}

	public VeiculoResponseDto toResponse(Veiculo veiculo) {

		VeiculoResponseDto response = new VeiculoResponseDto();

		response.setId(veiculo.getId());

		response.setTag(veiculo.getTag());
		response.setPlaca(veiculo.getPlaca());
		response.setMarca(veiculo.getMarca());
		response.setModelo(veiculo.getModelo());

		response.setAno(veiculo.getAno());

		response.setCapacidadeTotal(veiculo.getCapacidadeTotal());

		response.setKmAtual(veiculo.getKmAtual());

		response.setAtivo(veiculo.getAtivo());

		response.setDataCadastro(veiculo.getDataCadastro());

		return response;
	}

	@Override
	public void updateEntity(Veiculo entity, VeiculoRequestDto request) {

		//entity.setTag(request.getTag());
		//entity.setPlaca(request.getPlaca());
		entity.setMarca(request.getMarca());
		entity.setModelo(request.getModelo());

		entity.setAno(request.getAno());
		entity.setCapacidadeTotal(request.getCapacidadeTotal());

		if (request.getKmAtual() != null) {
		//	entity.setKmAtual(request.getKmAtual());
		}

		if (request.getAtivo() != null) {
			entity.setAtivo(request.getAtivo());
		}

	}

}
