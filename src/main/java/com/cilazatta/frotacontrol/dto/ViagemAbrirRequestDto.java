package com.cilazatta.frotacontrol.dto;

import java.math.BigDecimal;

import com.cilazatta.frotacontrol.enums.TipoViagem;

import lombok.Data;

@Data
public class ViagemAbrirRequestDto {
	
	  private Long veiculoId;

	  private Long kmInicial;

	  private TipoViagem tipoViagem;

	  private BigDecimal latitudeInicio;

	  private BigDecimal longitudeInicio;

}
