package com.cilazatta.frotacontrol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class VeiculoRequestDto {
	    
		@NotNull
		@Positive
	    private Short tag;

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

		@NotNull
		@Positive
	    private Long empresaId;
	    
	    private Boolean ativo;

}
