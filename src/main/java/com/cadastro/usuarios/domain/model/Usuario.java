package com.cadastro.usuarios.domain.model;

import javax.persistence.*;

import lombok.*;

@Entity(name= "t_usuarios")
@Table(name = "t_usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false,nullable = true)
    private Long id;
    @Column(name="nome")
    private String nome;
    @Column(name="sobrenome")
    private String sobrenome;
    @Column(name="email")
    private String email;
    @Column(name="idade")
    private int idade;
    @Column(name="ativo")
    private boolean ativo;

    public Usuario(String nome, String sobrenome, String email, int idade, boolean ativo) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.idade = idade;
        this.ativo = ativo;
    }
}