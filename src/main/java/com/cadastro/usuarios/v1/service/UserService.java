package com.cadastro.usuarios.v1.service;

import com.cadastro.usuarios.domain.model.DTO.UsuarioDTO;
import com.cadastro.usuarios.domain.model.Dependente;
import com.cadastro.usuarios.domain.model.Documento;
import com.cadastro.usuarios.domain.model.Usuario;
import com.cadastro.usuarios.domain.repository.DependenteRepository;
import com.cadastro.usuarios.domain.repository.DocumentoRepository;
import com.cadastro.usuarios.domain.repository.UserRepository;
import com.cadastro.usuarios.exception.CrudException;
import com.cadastro.usuarios.interf.Mappable;
import com.cadastro.usuarios.mapper.*;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements Mappable {

    UserRepository userRepository;

    DependenteRepository dependenteRepository;

    DocumentoRepository documentoRepository;

    public UsuarioDTO retrieve(Long id) {
        return new UsuarioDTO(userRepository.findUserById(id)
                .orElseThrow(() -> new CrudException(HttpStatus.NO_CONTENT, "Não existe usuário na base com este ID")),
                documentoRepository.findDocumentoByUserId(id));
    }

    public UsuarioDTO retrieveMapper(Long id) {
        return map(userRepository.findUserById(id)
                .orElseThrow(() -> new CrudException(HttpStatus.NO_CONTENT, "Não existe usuário na base com este ID")),UsuarioDTO.class);
    }


    public List<UsuarioDTO> retrieveAll() {
        List<UsuarioDTO> list = userRepository.findWholeListOfUsers().stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
        list.forEach(user -> user.setDocs(documentoRepository.findDocumentoByUserId(user.getId())));
        return list;
    }

    public List<UsuarioDTO> retrieveAllMapper() {
        return map(userRepository.findAll(),UsuarioDTO.class);
    }


    public Usuario save(Usuario user) {
        try {
            List<Documento> doc = new ArrayList<>();
            doc.addAll(user.getUserDoc());
            Dependente dep = user.getUserDep();
            user.setUserDep(null);
            user.setUserDoc(null);
            Usuario usuarioSalvo = userRepository.save(user);
            dep.setUser(usuarioSalvo);
            doc.forEach(documento -> documento.setUser(usuarioSalvo));
            usuarioSalvo.setUserDep(dependenteRepository.save(dep));
            usuarioSalvo.getUserDoc().addAll(documentoRepository.saveAll(doc));
            return usuarioSalvo;
        } catch (DataIntegrityViolationException ex) {
            throw new CrudException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
        } catch (IllegalArgumentException ex) {
            throw new CrudException(HttpStatus.BAD_REQUEST, "Usuario inválido");
        } catch (Exception ex) {
            throw new CrudException(HttpStatus.BAD_REQUEST, "Não foi possivel salvar o usuario");
        }
    }

    public UsuarioDTO update(Usuario user) {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new CrudException(HttpStatus.NO_CONTENT, "Usuário inexistente"));
        user.getUserDep().setUser(user);
        //user.getUserDoc().setUser(user);
        Usuario savedUser = userRepository.save(user);
        return UsuarioDTO.builder()
                .nomeCompleto(savedUser.getNome() + savedUser.getSobrenome())
                .email(savedUser.getEmail())
                .id(savedUser.getId())
                .build();
    }

    public void delete(Long id) {
        userRepository.findUserById(id)
                .orElseThrow(() -> new CrudException(HttpStatus.BAD_REQUEST, "Usuario não existe"));
        userRepository.deleteWholeUserById(id, id);
    }
}
