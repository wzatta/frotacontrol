package com.cilazatta.frotacontrol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VeiculoRequestDto {
	    
	@NotBlank
	    private String placa;

	    @NotBlank
	    private String marca;

	    @NotBlank
	    private String modelo;

	    private Integer ano;

	    @NotNull
	    private Integer capacidadeTotal;

	    private Long kmAtual;

	    private Boolean ativo;

}
