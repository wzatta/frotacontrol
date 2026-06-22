package com.cilazatta.frotacontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cilazatta.frotacontrol.entity.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    boolean existsByMatricula(String matricula);
}
