package com.cilazatta.frotacontrol.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FuncionarioRequestUpdateDto {

	  @NotBlank
	    private String matricula;

	    @NotBlank
	    private String nome;

	    private String telefone;

	    private String email;

	    private String logradouro;

	    private String numero;

	    private String bairro;

	    private String cidade;
	    
	    private String uf;

	    private String cep;
	 
	    private Boolean ativo;

}
