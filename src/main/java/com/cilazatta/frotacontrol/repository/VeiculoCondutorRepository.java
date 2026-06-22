package com.cilazatta.frotacontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cilazatta.frotacontrol.entity.VeiculoCondutor;

public interface VeiculoCondutorRepository extends JpaRepository<VeiculoCondutor, Long> {

	List<VeiculoCondutor> findByVeiculoId(Long veiculoId);

	List<VeiculoCondutor> findByFuncionarioId(Long funcionarioId);

	long countByVeiculoIdAndAtivoTrue(Long veiculoId);

	boolean existsByVeiculoIdAndFuncionarioId(Long veiculoId, Long funcionarioId);
}
