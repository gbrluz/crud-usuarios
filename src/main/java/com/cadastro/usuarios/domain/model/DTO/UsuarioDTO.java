package com.cadastro.usuarios.domain.model.DTO;

import com.cadastro.usuarios.domain.model.Dependente;
import com.cadastro.usuarios.domain.model.Documento;
import com.cadastro.usuarios.domain.model.Usuario;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsuarioDTO {
    @NotNull
    private Long id;
    @NotNull
    private String nomeCompleto;
    @NotNull
    private String email;
    @NotNull
    private int idade;
    private List<IDocumentoDTO> docs;
    private String nomeDependente;
    private Long idadeDependente;

    public UsuarioDTO(IUsuarioDTO u, List<IDocumentoDTO> docs) {
        this.id = u.getId();
        this.nomeCompleto = u.getNomeCompleto();
        this.email = u.getEmail();
        this.idade = u.getIdade().intValue();
        this.idadeDependente = u.getIdadeDependente();
        this.nomeDependente = u.getNomeDependente();
        this.docs = docs;
    }

    public UsuarioDTO(IUsuarioDTO u) {
        this.id = u.getId();
        this.nomeCompleto = u.getNomeCompleto();
        this.email = u.getEmail();
        this.idade = u.getIdade().intValue();
        this.idadeDependente = u.getIdadeDependente();
        this.nomeDependente = u.getNomeDependente();
    }
}
