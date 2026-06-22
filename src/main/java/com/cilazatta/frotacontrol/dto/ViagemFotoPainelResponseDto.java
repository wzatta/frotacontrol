package com.cilazatta.frotacontrol.dto;

import java.time.LocalDateTime;

import com.cilazatta.frotacontrol.enums.TipoFotoPainel;

import lombok.Data;

@Data
public class ViagemFotoPainelResponseDto {

    private Long id;

    private String urlArquivo;

    private Long kmLido;

    private Boolean validado;

    private TipoFotoPainel tipoFoto;
    
    private String justificativa;

    private LocalDateTime dataHora;
}