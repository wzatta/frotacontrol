package com.cilazatta.frotacontrol.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ViagemEncerrarRequestDto {

    @NotNull(message = "Km final é obrigatório")
    private Long kmFinal;

    @NotNull(message = "Latitude final é obrigatória")
    private Double latitudeFim;

    @NotNull(message = "Longitude final é obrigatória")
    private Double longitudeFim;
 
    @NotBlank
    private String urlFotoPainel;

    private Long kmLidoFoto;

    private String observacao;
    
    

}
