package com.cilazatta.frotacontrol.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "multa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Multa extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viagem_id", nullable = false)
    private Viagem viagem;

    private String numeroAuto;

    private String orgaoAutuador;

    private LocalDate dataInfracao;

    private BigDecimal valor;

    private String descricao;

    private String observacao;
}

