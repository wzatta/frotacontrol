package com.cilazatta.frotacontrol.mapper;

import org.springframework.stereotype.Component;

import com.cilazatta.frotacontrol.dto.UsuarioResponseDto;
import com.cilazatta.frotacontrol.entity.Usuario;

@Component
public class UsuarioMapper {

    public UsuarioResponseDto toResponse(Usuario usuario) {

        return UsuarioResponseDto.builder()
                .id(usuario.getId())
                .name(usuario.getName())
                .login(usuario.getLogin())
                .ativo(usuario.getAtivo())

                .empresaId(
                        usuario.getEmpresa() != null
                                ? usuario.getEmpresa().getId()
                                : null)

                .empresaNome(
                        usuario.getEmpresa() != null
                                ? usuario.getEmpresa().getNomeFantasia()
                                : null)

                .funcionarioId(
                        usuario.getFuncionario() != null
                                ? usuario.getFuncionario().getId()
                                : null)

                .funcionarioNome(
                        usuario.getFuncionario() != null
                                ? usuario.getFuncionario().getNome()
                                : null)

                .roles(usuario.getRoles())

                .build();
    }
}                               