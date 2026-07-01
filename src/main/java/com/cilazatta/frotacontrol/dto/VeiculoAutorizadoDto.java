package com.cilazatta.frotacontrol.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoAutorizadoDto {
    private Long id;
    private String placa;
    private String modelo;
    private Short tag;
    private Long hodometroAtual; // Mapeia o kmAtual do banco
}
