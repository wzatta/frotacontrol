package com.cilazatta.frotacontrol.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cilazatta.frotacontrol.enums.StatusViagem;
import com.cilazatta.frotacontrol.enums.TipoViagem;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "viagem")
@Getter
@Setter
public class Viagem extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorista_id", nullable = false)
    private Funcionario motorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rota_id")
    private Rota rota;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoViagem tipoViagem;

    @Column(nullable = false, length = 200)
    private String finalidade;

    @Column(length = 200)
    private String origem;

    @Column(length = 200)
    private String destino;
    
    private String latitude_inicio;
    private String longitude_inicio;

    private String latitude_fim;
    
    private String longitude_fim;

    @Column(nullable = false)
    private Long kmInicial;

    private Long kmFinal;

    @Column(nullable = false)
    private LocalDateTime dataHoraSaida;

    private LocalDateTime dataHoraChegada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusViagem status = StatusViagem.ABERTA;

    @Column(length = 500)
    private String observacao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
    
	@OneToMany(mappedBy = "viagem", cascade = CascadeType.PERSIST)
	private List<ViagemParticipante> passageiros = new ArrayList<>();

	public void setPassageiros(List<ViagemParticipante> passageiros) {
		if (passageiros == null) {
			throw new IllegalArgumentException("Passageiros da Rota não pode ser null");
		}

		this.passageiros.clear();
		this.passageiros.addAll(passageiros);
	}

	public void addPassageiro(ViagemParticipante passageiro) {
		if (passageiro == null) {
			throw new IllegalArgumentException("Passageiro não pode ser null");
		}
		passageiro.setViagem(this);
		
		this.passageiros.add(passageiro);
	}

	public void removePassageiro(ViagemParticipante passageiro) {
		if (passageiro == null) {
			throw new IllegalArgumentException("Passageiro não pode ser null");
		}
		passageiro.setViagem(null);
		this.passageiros.remove(passageiro);
	}
    
    

}