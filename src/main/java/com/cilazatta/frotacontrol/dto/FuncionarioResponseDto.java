package com.cilazatta.frotacontrol.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FuncionarioResponseDto {
	 
	    private Long id;

	    private String matricula;

	    private String nome;

	    private String cpf;

	    private String telefone;

	    private String email;

	    private String logradouro;

	    private String numero;

	    private String bairro;

	    private String cidade;

	    private String cep;

	    private Boolean ativo;

	    private LocalDateTime dataCadastro;

}
