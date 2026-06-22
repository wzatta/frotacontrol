package com.cilazatta.frotacontrol.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cilazatta.frotacontrol.entity.Viagem;
import com.cilazatta.frotacontrol.enums.StatusViagem;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long> {

Optional<Viagem> findByVeiculoIdAndStatus(
    Long veiculoId,
    StatusViagem status);

}