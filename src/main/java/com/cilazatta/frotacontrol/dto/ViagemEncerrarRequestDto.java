package com.cilazatta.frotacontrol.dto;


import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ViagemEncerrarRequestDto {

    @NotNull(message = "Km final é obrigatório")
    private Long kmFinal;

    @NotNull(message = "Latitude final é obrigatória")
    private BigDecimal latitudeFim;

    @NotNull(message = "Longitude final é obrigatória")
    private BigDecimal longitudeFim;
 
    @NotBlank
    private String urlFotoPainel;

    private Long kmLidoFoto;

    private String observacao;
    
    

}
