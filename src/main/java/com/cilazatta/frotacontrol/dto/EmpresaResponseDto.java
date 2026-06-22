package com.cilazatta.frotacontrol.dto;


import lombok.Data;

@Data
public class EmpresaResponseDto {

    private Long id;

    private String razaoSocial;

    private String nomeFantasia;

    private String cnpj;
    
    private String contato;

    private String email;

    private String telefone;

    private Boolean ativa;
}