package com.cilazatta.frotacontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cilazatta.frotacontrol.entity.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    boolean existsByPlaca(String placa);
}
