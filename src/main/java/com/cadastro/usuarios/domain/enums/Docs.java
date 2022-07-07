package com.cadastro.usuarios.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@Getter
@ToString
public enum Docs {
    RG( "Registro Geral", "RG"),
    CPF( "Cadatro de Pessoa Física", "CPF"),
    CNH( "Carteira Nacional de Habilitação", "SJS"),
    PASSAPORTE( "Passaporte", "DETRAN"),
    CARTEIRA_DE_TRABALHO( "Carteira de Trabalho", "CTPS");

    private String nome;
    private String sigla;
}
