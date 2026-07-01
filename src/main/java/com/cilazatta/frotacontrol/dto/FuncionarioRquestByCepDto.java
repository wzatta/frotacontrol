package com.cilazatta.frotacontrol.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FuncionarioRquestByCepDto {
	
	@NotBlank
    private String matricula;

    @NotBlank
    private String nome;

    private String telefone;

    private String email;

    @NotBlank
    private String cep;
    
    @NotBlank
    private String numero;

    private Long empresaId;
 
    private Boolean ativo;

}
