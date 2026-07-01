package com.cilazatta.frotacontrol.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cilazatta.frotacontrol.entity.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    boolean existsByMatricula(String matricula);
    
    List<Funcionario> findByEmpresaIdOrderByNomeAsc(Long empresaId);
    
    Optional<Funcionario> findByIdAndEmpresaId(Long id, Long empresaId);
}
