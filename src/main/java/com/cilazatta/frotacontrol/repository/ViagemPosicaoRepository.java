package com.cilazatta.frotacontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cilazatta.frotacontrol.entity.ViagemPosicao;

public interface ViagemPosicaoRepository extends JpaRepository<ViagemPosicao, Long> {

	List<ViagemPosicao> findByViagemIdOrderByDataHora(Long viagemId);

}