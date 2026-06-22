package com.cilazatta.frotacontrol.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "veiculo")
@Getter
@Setter
public class Veiculo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(nullable = false, unique = true, length = 10)
    private String placa;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    private Integer ano;

    @Column(nullable = false)
    private Integer capacidadeTotal;

    @Column(nullable = false)
    private Long kmAtual = 0L;

    @Column(nullable = false)
    private Boolean ativo = true;
}
