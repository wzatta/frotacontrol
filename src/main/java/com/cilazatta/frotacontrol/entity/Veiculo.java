package com.cilazatta.frotacontrol.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
	    name = "veiculo",
	    uniqueConstraints = {
	        @UniqueConstraint(
	            name = "uk_veiculo_empresa_placa",
	            columnNames = {
	                "empresa_id",
	                "placa"
	            }
	        )
	    }
	)
@Getter
@Setter
public class Veiculo extends BaseEntity {

	
	@Column(nullable = false, unique = true, length = 4)
	private Short tag;
	
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
    
    
}
