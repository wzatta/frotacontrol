package com.cilazatta.frotacontrol.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cilazatta.frotacontrol.enums.TipoViagem;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViagemRequestDto {

	@Positive
    private Long veiculoId;

    private Long motoristaId;

    private Long rotaId;

    private TipoViagem tipoViagem;
      
    private BigDecimal latitudeInicio;

    private BigDecimal longitudeInicio;

    private BigDecimal latitudeFinal;
    
    private BigDecimal longitudeFinal;
    
    private Long kmInicial;
    
    private Long kmFinal;

    private String observacao;

    private List<ViagemParticipanteRequestDto>
            participantes = new ArrayList<>();

	public ViagemRequestDto(Long veiculoId) {
		super();
		this.veiculoId = veiculoId;
	}
}