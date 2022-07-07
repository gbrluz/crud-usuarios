package com.cadastro.usuarios.domain.repository;

import com.cadastro.usuarios.domain.model.DTO.IUsuarioDTO;
import com.cadastro.usuarios.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
    //Criar DTOs que mostrem a informação útil na response para os métodos save e update
    //Criar queries nativas que retornem Projeções para os métodos
    //findAll e FindById
    //Criar métodos para obter somente documento e dependente
    //criar método para obter Somente documento especifico

    @Query(value = "select t_usuarios.id,"
            + "concat(t_usuarios.nome,' ',t_usuarios.sobrenome) as nomeCompleto,"
            + "t_usuarios.email,"
            + "t_usuarios.idade,"
            + "case "
            + "when concat(t_dependentes.dep_nome,' ',t_dependentes.dep_sobrenome) = ' '"
            + "then 'Não há dependente' "
            + "else concat(t_dependentes.dep_nome,' ',t_dependentes.dep_sobrenome)"
            + "end as nomeDependente,"
            + "t_dependentes.dep_idade as idadeDependente "
            + "from t_usuarios "
            + "left join t_dependentes on t_dependentes.user_id = t_usuarios.id "
            + "where t_usuarios.id = :id"
            , nativeQuery = true)
    Optional<IUsuarioDTO> findUserById(@Param("id") final Long id);


    @Query(value = "select t_usuarios.id,"
            + "concat(t_usuarios.nome,' ',t_usuarios.sobrenome) as nomeCompleto,"
            + "t_usuarios.email,"
            + "t_usuarios.idade,"
            + "case "
            + "when concat(t_dependentes.dep_nome,' ',t_dependentes.dep_sobrenome) = ' '"
            + "then 'Não há dependente' "
            + "else concat(t_dependentes.dep_nome,' ',t_dependentes.dep_sobrenome)"
            + "end as nomeDependente,"
            + "t_dependentes.dep_idade as idadeDependente "
            + "from t_usuarios "
            + "left join t_dependentes on t_dependentes.user_id = t_usuarios.id ",
            nativeQuery = true)
    List<IUsuarioDTO> findWholeListOfUsers();




    @Transactional
    @Modifying
    @Query(value = "delete from t_documentos inner join t_documentos on t_usuarios.id=t_documentos.user_id "
            + "where t_documentos.user_id = :userId; "
            + "delete from t_dependentes inner join t_dependentes on t_usuarios.id=t_dependentes.user_id"
            + "where t_dependentes.user_id = :userId; "
            + "delete from t_usuarios where t_usuarios.id = :id); ",
            nativeQuery = true)
    void deleteWholeUserById(@Param("id") final Long id,@Param("userId") final Long userId);

}
