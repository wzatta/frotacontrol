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
@Table(name = "funcionario")
@Getter
@Setter
public class Funcionario extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String matricula;

    @Column(nullable = false, length = 150)
    private String nome;

    private String telefone;

    private String email;

    private String logradouro;

    private String numero;

    private String bairro;

    private String cidade;

    private String cep;

    @Column(nullable = false)
    private Boolean ativo = true;
}
