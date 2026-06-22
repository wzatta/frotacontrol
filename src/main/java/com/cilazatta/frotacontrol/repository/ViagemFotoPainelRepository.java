package com.cilazatta.frotacontrol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cilazatta.frotacontrol.entity.ViagemFotoPainel;
import com.cilazatta.frotacontrol.enums.TipoFotoPainel;

public interface ViagemFotoPainelRepository extends JpaRepository<ViagemFotoPainel, Long> {

	List<ViagemFotoPainel> findByViagemId(Long viagemId);

	ViagemFotoPainel findFirstByViagemIdAndTipoFotoOrderByDataHoraDesc(Long viagemId, TipoFotoPainel tipoFoto);

}