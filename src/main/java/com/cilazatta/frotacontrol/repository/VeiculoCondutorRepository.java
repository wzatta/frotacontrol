package com.cilazatta.frotacontrol.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cilazatta.frotacontrol.entity.VeiculoCondutor;

public interface VeiculoCondutorRepository extends JpaRepository<VeiculoCondutor, Long> {

	List<VeiculoCondutor> findByVeiculoId(Long veiculoId);

	List<VeiculoCondutor> findByFuncionarioId(Long funcionarioId);
	
	

	long countByVeiculoIdAndAtivoTrue(Long veiculoId);

	boolean existsByVeiculoIdAndFuncionarioId(Long veiculoId, Long funcionarioId);
	
	List<VeiculoCondutor> findByVeiculoIdAndAtivoTrue(
            Long veiculoId);

    List<VeiculoCondutor> findByFuncionarioIdAndAtivoTrue(
            Long funcionarioId);

    Optional<VeiculoCondutor> findByVeiculoIdAndFuncionarioId(
            Long veiculoId,
            Long funcionarioId);

    boolean existsByVeiculoIdAndFuncionarioIdAndAtivoTrue(
            Long veiculoId,
            Long funcionarioId);
}
