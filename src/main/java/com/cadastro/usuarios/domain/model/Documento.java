package com.cadastro.usuarios.domain.model;


import com.cadastro.usuarios.domain.enums.Docs;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "documento")
@Table(name = "documento")
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
    private Docs tipo;
    @Column(name = "data_de_exp")
    private String dataDeExpedicao;
    @Column(name = "orgao_exp")
    private int orgaoExpeditor;
    @Column(name = "validade")
    private String validade;

}
