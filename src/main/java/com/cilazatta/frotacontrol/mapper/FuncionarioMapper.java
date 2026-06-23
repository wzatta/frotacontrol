package com.cilazatta.frotacontrol.mapper;

import org.springframework.stereotype.Component;

import com.cilazatta.frotacontrol.dto.FuncionarioRequestDto;
import com.cilazatta.frotacontrol.dto.FuncionarioRequestUpdateDto;
import com.cilazatta.frotacontrol.dto.FuncionarioResponseDto;
import com.cilazatta.frotacontrol.entity.Funcionario;

@Component
public class FuncionarioMapper implements BaseMapper<Funcionario, FuncionarioRequestDto , FuncionarioResponseDto,FuncionarioRequestUpdateDto > {
	
	 public Funcionario toEntity(FuncionarioRequestDto request) {

	        Funcionario funcionario = new Funcionario();

	        funcionario.setMatricula(request.getMatricula());
	        funcionario.setNome(request.getNome());

	        funcionario.setTelefone(request.getTelefone());
	        funcionario.setEmail(request.getEmail());

	        funcionario.setLogradouro(request.getLogradouro());
	        funcionario.setNumero(request.getNumero());
	        funcionario.setBairro(request.getBairro());
	        funcionario.setCidade(request.getCidade());
	        funcionario.setUf(request.getUf());
	        funcionario.setCep(request.getCep());

	        funcionario.setAtivo(
	                request.getAtivo() == null ? true : request.getAtivo()
	        );

	        return funcionario;
	    }

	 public FuncionarioResponseDto toResponse(Funcionario funcionario) {

	        FuncionarioResponseDto response = new FuncionarioResponseDto();

	        response.setId(funcionario.getId());

	        response.setMatricula(funcionario.getMatricula());
	        response.setNome(funcionario.getNome());

	        response.setTelefone(funcionario.getTelefone());
	        response.setEmail(funcionario.getEmail());

	        response.setLogradouro(funcionario.getLogradouro());
	        response.setNumero(funcionario.getNumero());
	        response.setBairro(funcionario.getBairro());
	        response.setCidade(funcionario.getCidade());
	        response.setUf(funcionario.getUf());
	        response.setCep(funcionario.getCep());

	        response.setAtivo(funcionario.getAtivo());
	        
	        response.setCnpj(funcionario.getEmpresa().getCnpj());
	        
	        response.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());

	        response.setDataCadastro(funcionario.getDataCadastro());

	        return response;
	    }

	
	    @Override
	    public void updateEntity(
	            Funcionario funcionario,
	            FuncionarioRequestUpdateDto request) {

	        funcionario.setMatricula(request.getMatricula());
	        funcionario.setNome(request.getNome());

	        funcionario.setTelefone(request.getTelefone());
	        funcionario.setEmail(request.getEmail());

	        funcionario.setLogradouro(request.getLogradouro());
	        funcionario.setNumero(request.getNumero());
	        funcionario.setBairro(request.getBairro());
	        funcionario.setCidade(request.getCidade());
	        funcionario.setUf(request.getUf());
	        funcionario.setCep(request.getCep());

	        funcionario.setAtivo(
	                request.getAtivo() == null
	                        ? true
	                        : request.getAtivo()
	        );
	    
	    }
}
