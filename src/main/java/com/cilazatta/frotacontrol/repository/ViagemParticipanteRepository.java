package com.cilazatta.frotacontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cilazatta.frotacontrol.entity.ViagemParticipante;

public interface ViagemParticipanteRepository extends JpaRepository<ViagemParticipante, Long> {

	List<ViagemParticipante> findByViagemId(Long viagemId);

}
