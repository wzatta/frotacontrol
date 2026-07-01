package com.cilazatta.frotacontrol.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cilazatta.frotacontrol.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
   
	Optional<Usuario> findByLogin(String login);
	
	Optional<Usuario> findByLoginAndEmpresaId(String login, Long empresaId);
   
	Optional<Usuario> findByIdAndEmpresaId(Long id, Long empresaId);
	
	Optional<Usuario> findByFuncionarioIdAndEmpresaId(Long funcionarioId, Long empresaId);
    
}