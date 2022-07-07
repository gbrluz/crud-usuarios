package com.cadastro.usuarios.domain.repository;

import com.cadastro.usuarios.domain.model.DTO.IDocumentoDTO;
import com.cadastro.usuarios.domain.model.Documento;
import com.cadastro.usuarios.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    @Query(value =
            "select  t_documentos.id,t_documentos.numero ,t_documentos.tipo, "
                    + "t_documentos.data_de_exp dataDeExpedicao, orgaos_exp. "
                    + "nome as "
                    + "orgaoExpeditor, t_documentos.validade from "
                    + "t_documentos "
                    + "join orgaos_exp "
                    + "on orgaos_exp.id = "
                    + "t_documentos.orgao_exp where "
                    + "t_documentos.user_id =:userId "
            , nativeQuery = true)
    List<IDocumentoDTO> findDocumentoByUserId(@Param("userId") final Long userId);
}
