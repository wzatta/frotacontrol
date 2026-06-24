package com.cilazatta.frotacontrol.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cilazatta.frotacontrol.entity.VeiculoCondutor;

public interface VeiculoCondutorRepository extends JpaRepository<VeiculoCondutor, Long> {

    /**
     * Retorna todos os condutores vinculados ao veículo,
     * independentemente de estarem ativos ou inativos.
     */
    List<VeiculoCondutor> findByVeiculoId(Long veiculoId);

    /**
     * Retorna todos os veículos autorizados para o funcionário,
     * independentemente de estarem ativos ou inativos.
     */
    List<VeiculoCondutor> findByFuncionarioId(Long funcionarioId);

    /**
     * Conta quantos condutores ativos estão autorizados
     * para dirigir o veículo.
     *
     * Utilizado para validar o limite máximo de condutores
     * autorizados por veículo.
     */
    long countByVeiculoIdAndAtivoTrue(Long veiculoId);

    /**
     * Verifica se existe vínculo entre o veículo e o funcionário,
     * independentemente do status da autorização.
     *
     * Utilizado para evitar duplicidade de cadastro.
     */
    boolean existsByVeiculoIdAndFuncionarioId(
            Long veiculoId,
            Long funcionarioId);

    /**
     * Retorna apenas os condutores ativos autorizados
     * para dirigir o veículo.
     *
     * Utilizado principalmente na abertura de viagens
     * e consultas operacionais.
     */
    List<VeiculoCondutor> findByVeiculoIdAndAtivoTrue(
            Long veiculoId);

    /**
     * Retorna apenas os vínculos ativos do funcionário
     * com veículos autorizados.
     *
     * Permite identificar quais veículos o funcionário
     * pode conduzir atualmente.
     */
    List<VeiculoCondutor> findByFuncionarioIdAndAtivoTrue(
            Long funcionarioId);

    /**
     * Busca um vínculo específico entre veículo e funcionário.
     *
     * Retorna o registro mesmo que esteja inativo.
     *
     * Utilizado em validações e manutenção cadastral.
     */
    Optional<VeiculoCondutor> findByVeiculoIdAndFuncionarioId(
            Long veiculoId,
            Long funcionarioId);

    /**
     * Verifica se o funcionário possui autorização ativa
     * para conduzir o veículo informado.
     *
     * Principal método utilizado na abertura de viagem.
     */
    boolean existsByVeiculoIdAndFuncionarioIdAndAtivoTrue(
            Long veiculoId,
            Long funcionarioId);
}