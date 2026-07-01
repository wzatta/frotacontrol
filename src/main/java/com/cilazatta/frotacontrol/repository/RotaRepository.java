package com.cilazatta.frotacontrol.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cilazatta.frotacontrol.entity.Rota;

public interface RotaRepository extends JpaRepository<Rota, Long> {

	List<Rota> findByAtivaTrue();

	List<Rota> findByEmpresaIdOrderByDescricaoAsc(Long empresaId);

	boolean existsByDescricaoIgnoreCase(String descricao);

	Optional<Rota> findByIdAndEmpresaId(Long rotaId, Long empresaId);

	boolean existsByDescricaoIgnoreCaseAndEmpresaId(String descricao, Long empresaId);

}
