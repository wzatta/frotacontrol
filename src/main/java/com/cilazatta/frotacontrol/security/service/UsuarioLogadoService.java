package com.cilazatta.frotacontrol.security.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cilazatta.frotacontrol.entity.Usuario;
import com.cilazatta.frotacontrol.enums.Role;

import io.jsonwebtoken.Claims;

@Service
public class UsuarioLogadoService {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
    public Usuario getUsuario() {
        return (Usuario) getAuthentication().getPrincipal();
    }

    public Claims getClaims() {
        return (Claims) getAuthentication().getDetails();
    }

    public String getLogin() {
        return getAuthentication().getName();
    }

//    public Long getEmpresaId() {
//        Claims claims = (Claims) getAuthentication().getDetails();
//        return claims.get("empresaId", Long.class);
//    }
    
    public boolean hasRole(Role role) {
        return getRoles().contains(role);
    }
    
    public Long getEmpresaId() {
        return getUsuario().getEmpresa().getId();
    }
    
    public List<Role> getRoles() {
        return getAuthentication()
                .getAuthorities()
                .stream()
                .map(a -> Role.valueOf(a.getAuthority()))
                .toList();
    }
}