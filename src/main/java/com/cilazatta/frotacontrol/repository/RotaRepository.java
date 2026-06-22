package com.cilazatta.frotacontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cilazatta.frotacontrol.entity.Rota;

public interface RotaRepository extends JpaRepository<Rota, Long> {

    List<Rota> findByAtivaTrue();

    boolean existsByDescricaoIgnoreCase(String descricao);

}
