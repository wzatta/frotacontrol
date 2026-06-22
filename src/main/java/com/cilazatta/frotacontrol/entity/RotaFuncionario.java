package com.cilazatta.frotacontrol.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "rota_funcionario",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_rota_funcionario",
            columnNames = {
                "rota_id",
                "funcionario_id"
            }
        )
    }
)
@Getter
@Setter
public class RotaFuncionario extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rota_id", nullable = false)
    private Rota rota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(nullable = false)
    private Integer ordem;

    @Column(nullable = false)
    private Boolean ativo = true;
}