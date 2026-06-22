package com.cilazatta.frotacontrol.entity;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "viagem_participante",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_viagem_funcionario",
            columnNames = {
                "viagem_id",
                "funcionario_id"
            }
        )
    }
)
@Getter
@Setter
public class ViagemParticipante extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viagem_id", nullable = false)
    private Viagem viagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(nullable = false)
    private Boolean presente = false;

    private LocalDateTime dataHoraConfirmacao;

    @Column(length = 200)
    private String observacao;
}