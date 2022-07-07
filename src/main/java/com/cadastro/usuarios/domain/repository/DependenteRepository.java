package com.cadastro.usuarios.domain.repository;

import com.cadastro.usuarios.domain.model.DTO.IDependenteDTO;
import com.cadastro.usuarios.domain.model.DTO.IUsuarioDTO;
import com.cadastro.usuarios.domain.model.Dependente;
import com.cadastro.usuarios.domain.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DependenteRepository extends JpaRepository<Dependente, Long> {
    @Query(value = "select t_dependentes.id , "
            + "concat(t_dependentes.dep_nome,' ',t_dependentes.dep_sobrenome) as nomeDependente, "
            + "t_dependentes.dep_idade as idadeDepdendente "
            + "from t_dependentes "
            + "where t_dependentes.user_id = :id "
            , nativeQuery = true)
    Optional<IDependenteDTO> findDepByUserId(@Param("id") final Long id);

}
