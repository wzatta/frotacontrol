package com.cilazatta.frotacontrol.mapper;

import org.springframework.stereotype.Component;

import com.cilazatta.frotacontrol.dto.EmpresaRequestDto;
import com.cilazatta.frotacontrol.dto.EmpresaResponseDto;
import com.cilazatta.frotacontrol.entity.Empresa;

@Component
public class EmpresaMapper {

	public Empresa toEntity(EmpresaRequestDto request) {

		Empresa empresa = new Empresa();

		updateEntity(empresa, request);

		return empresa;
	}

	public EmpresaResponseDto toResponse(Empresa entity) {

		EmpresaResponseDto dto = new EmpresaResponseDto();

		dto.setId(entity.getId());
		dto.setRazaoSocial(entity.getRazaoSocial());
		dto.setNomeFantasia(entity.getNomeFantasia());
		dto.setCnpj(entity.getCnpj());
		dto.setContato(entity.getContato());
		dto.setEmail(entity.getEmail());
		dto.setTelefone(entity.getTelefone());
		dto.setAtiva(entity.getAtiva());

		return dto;
	}

	public void updateEntity(Empresa entity, EmpresaRequestDto request) {

		entity.setRazaoSocial(request.getRazaoSocial());

		entity.setNomeFantasia(request.getNomeFantasia());

		entity.setCnpj(request.getCnpj());
		
		entity.setContato(request.getContato());

		entity.setEmail(request.getEmail());

		entity.setTelefone(request.getTelefone());

		entity.setAtiva(request.getAtiva());
	}
}
