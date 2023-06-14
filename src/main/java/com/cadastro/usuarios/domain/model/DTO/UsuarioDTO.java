package com.cadastro.usuarios.domain.model.DTO;

import lombok.*;

import javax.validation.constraints.NotNull;

import java.util.List;

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
