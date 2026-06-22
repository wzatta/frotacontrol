package com.cilazatta.frotacontrol.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
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
@Table(name = "viagem_posicao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViagemPosicao extends BaseEntity {
		
    @ManyToOne
    @JoinColumn(name = "viagem_id")
    private Viagem viagem;

    private Double latitude;

    private Double longitude;

    private Double velocidade;

    private LocalDateTime dataHora;
}