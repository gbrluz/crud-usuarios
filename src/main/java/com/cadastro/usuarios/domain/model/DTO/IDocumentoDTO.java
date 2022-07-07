package com.cadastro.usuarios.domain.model.DTO;

import com.cadastro.usuarios.domain.enums.Docs;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public interface IDocumentoDTO {
    Long getId();

    String getNumero();

    @JsonIgnore
    Docs getTipo();

     default String getTipoDescricao() {
        return this.getTipo().getNome();
    }


    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate getDataDeExpedicao();

    String getOrgaoExpeditor();

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate getValidade();


}
