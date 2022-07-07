package com.cadastro.usuarios.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "t_dependentes")
@Table(name = "t_dependentes")
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
    @Column(name = "dep_nome")
    private String depNome;
    @Column(name = "dep_sobrenome")
    private String depSobrenome;
    @Column(name = "dep_idade")
    private int depIdade;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = LAZY)
    private Usuario user;

}

