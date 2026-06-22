package com.cilazatta.frotacontrol.entity;


import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rota")
@Getter
@Setter
public class Rota extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false, length = 100)
    private String descricao;

    @Column(nullable = false)
    private Boolean ativa = true;
    
	@OneToMany(mappedBy = "rota", cascade = CascadeType.PERSIST)
	private List<RotaFuncionario> passageiros = new ArrayList<>();

	public void setPassageiros(List<RotaFuncionario> passageiros) {
		if (passageiros == null) {
			throw new IllegalArgumentException("Passageiros da Rota não pode ser null");
		}

		this.passageiros.clear();
		this.passageiros.addAll(passageiros);
	}

	public void addPassageiro(RotaFuncionario passageiro) {
		if (passageiro == null) {
			throw new IllegalArgumentException("Passageiro não pode ser null");
		}
		passageiro.setRota(this);
		
		this.passageiros.add(passageiro);
	}

	public void removePassageiro(RotaFuncionario passageiro) {
		if (passageiro == null) {
			throw new IllegalArgumentException("Passageiro não pode ser null");
		}
		passageiro.setRota(null);
		this.passageiros.remove(passageiro);
	}
}