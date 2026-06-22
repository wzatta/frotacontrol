package com.cilazatta.frotacontrol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmpresaRequestDto {

    @NotBlank
    @Size(max = 150)
    private String razaoSocial;

    @NotBlank
    @Size(max = 150)
    private String nomeFantasia;

    @NotBlank
    @Size(max = 18)
    private String cnpj;

    @Size(max = 50)
    private String contato;

    @Size(max = 100)
    private String email;

    @Size(max = 20)
    private String telefone;

    private Boolean ativa = true;
}
