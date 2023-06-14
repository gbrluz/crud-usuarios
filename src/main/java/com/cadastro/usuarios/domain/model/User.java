package com.cadastro.usuarios.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "user")
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "sobrenome")
    private String sobrenome;
    @Column(name = "email")
    private String email;
    @Column(name = "idade")
    private int idade;
    @Column(name = "ativo")
    @NotNull
    private boolean ativo;

    @OneToMany(fetch = LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Documento> userDoc;

    @OneToOne(fetch = LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Dependente userDep;

    public User(Long id, String nome, String sobrenome, String email, int idade, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.idade = idade;
        this.ativo = ativo;
    }

    public User(String nome, String sobrenome, String email, int idade, boolean ativo) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.idade = idade;
        this.ativo = ativo;
    }

}