package com.cilazatta.frotacontrol.mapper;

import java.util.ArrayList;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cilazatta.frotacontrol.dto.RegisterRequestDto;
import com.cilazatta.frotacontrol.entity.Funcionario;
import com.cilazatta.frotacontrol.entity.Usuario;

@Component
public class RegisterRequestMapper {
	
	private final PasswordEncoder passwordEncoder;
	
	 public RegisterRequestMapper(PasswordEncoder passwordEncoder) {
	        this.passwordEncoder = passwordEncoder;
	    }

	public Usuario toEntity(RegisterRequestDto dto, Funcionario colaborador) {
	    return Usuario.builder()
	            .funcionario(colaborador)
	            .name(dto.getName())
	            .login(dto.getLogin())
	            .senha(passwordEncoder.encode(dto.getPassword()))
	            .ativo(dto.getAtivo())
	            .roles(new ArrayList<>(dto.getRoles())) 
	            .build();
	}

	
	
}