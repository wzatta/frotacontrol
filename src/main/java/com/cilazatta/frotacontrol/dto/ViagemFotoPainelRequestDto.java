package com.cilazatta.frotacontrol.dto;

import com.cilazatta.frotacontrol.enums.TipoFotoPainel;

import lombok.Data;

@Data
public class ViagemFotoPainelRequestDto {

    private Long viagemId;

    private String urlArquivo;

    private Long kmLido;

    private Boolean validado;

    private TipoFotoPainel tipoFoto;
}