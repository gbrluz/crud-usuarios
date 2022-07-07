package com.cadastro.usuarios.domain.model;


import com.cadastro.usuarios.domain.enums.Docs;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "t_documentos")
@Table(name = "t_documentos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties({ "user" })
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;
    @Column(name = "numero")
    private String numero;
    @Column(name = "tipo")
    //Tipo devera ser um enum
    private Docs tipo;
    @Column(name = "data_de_exp")
    private String dataDeExpedicao;
    //Orgao sera um link para uma tabela de orgaos
    @Column(name = "orgao_exp")
    private int orgaoExpeditor;
    @Column(name = "validade")
    private String validade;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = LAZY)
    private Usuario user;
}
