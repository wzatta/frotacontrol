package com.cilazatta.frotacontrol.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class VeiculoResponseDto {

	  private Long id;

	    private String placa;

	    private String marca;

	    private String modelo;

	    private Integer ano;

	    private Integer capacidadeTotal;

	    private Long kmAtual;

	    private Boolean ativo;

	    private LocalDateTime dataCadastro;
	
}
