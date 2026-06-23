package com.cilazatta.frotacontrol.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cilazatta.frotacontrol.entity.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    boolean existsByPlaca(String placa);
    boolean existsByPlacaAndEmpresaId(String placa, Long empresaId);
    
    Optional<Veiculo> findByIdAndEmpresaId(
            Long id,
            Long empresaId);
    
    List<Veiculo> findByEmpresaId(
            Long empresaId);
    
    
    
}
