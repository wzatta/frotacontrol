package com.cilazatta.frotacontrol.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "empresa")
@Getter
@Setter
public class Empresa extends BaseEntity {

     @Column(nullable = false, length = 150)
    private String razaoSocial;

    @Column(nullable = false, length = 150)
    private String nomeFantasia;

    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(length = 50)
    private String contato;
    
    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefone;

    private Boolean ativa = true;
}