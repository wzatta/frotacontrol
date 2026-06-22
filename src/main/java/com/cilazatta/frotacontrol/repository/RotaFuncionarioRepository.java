package com.cilazatta.frotacontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cilazatta.frotacontrol.entity.RotaFuncionario;

public interface RotaFuncionarioRepository extends JpaRepository<RotaFuncionario, Long> {

	List<RotaFuncionario> findByRotaIdOrderByOrdem(Long rotaId);

	boolean existsByRotaIdAndFuncionarioId(Long rotaId, Long funcionarioId);

}