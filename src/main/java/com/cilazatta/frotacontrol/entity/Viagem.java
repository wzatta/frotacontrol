package com.cilazatta.frotacontrol.entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cilazatta.frotacontrol.enums.StatusViagem;
import com.cilazatta.frotacontrol.enums.TipoViagem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
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


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoViagem tipoViagem;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude_inicio;
    
    @Column(precision = 10, scale = 8)
    private BigDecimal longitude_inicio;
    
    private String localPartida;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude_fim;
    
    @Column(precision = 10, scale = 8)
    private BigDecimal longitude_fim;

    @Column(nullable = false)
    private Long kmInicial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rota_id")
    private Rota rota;
    
    private Long kmFinal;

    @Column(nullable = false)
    private LocalDateTime dataHoraAbertura;

    private LocalDateTime dataHoraEncerramento;

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

	public Viagem(Veiculo veiculo, Funcionario motorista, Empresa empresa) {
		super();
		this.veiculo = veiculo;
		this.motorista = motorista;
		this.empresa = empresa;
	}
    
    

}