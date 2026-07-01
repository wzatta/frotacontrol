package com.cilazatta.frotacontrol.dto;


import java.util.List;

import com.cilazatta.frotacontrol.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDto {

    private Long id;

    private String name;

    private String login;

    private Boolean ativo;

    private Long empresaId;

    private String empresaNome;

    private Long funcionarioId;

    private String funcionarioNome;

    private List<Role> roles;
}
