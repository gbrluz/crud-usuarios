package com.cadastro.usuarios.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "dependente")
@Table(name = "dependente")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"user"})
public class Dependente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "vinculo")
    private String vinculo;
    @Column(name = "idade")
    private int idade;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = LAZY)
    private User user;

}

