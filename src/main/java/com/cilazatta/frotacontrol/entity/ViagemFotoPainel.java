package com.cilazatta.frotacontrol.entity;

import java.time.LocalDateTime;

import com.cilazatta.frotacontrol.enums.TipoFotoPainel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "viagem_foto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViagemFotoPainel extends BaseEntity {

	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "viagem_id", nullable = false)
    private Viagem viagem;

    private String urlArquivo;

    private Long kmLido;

    private Boolean validado;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoFotoPainel tipoFoto;

    @Column(nullable = false)
    private LocalDateTime dataHora;
}