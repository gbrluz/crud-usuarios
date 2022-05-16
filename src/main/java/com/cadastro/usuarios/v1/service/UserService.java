package com.cadastro.usuarios.v1.service;

import com.cadastro.usuarios.domain.model.Usuario;
import com.cadastro.usuarios.domain.repository.UserRepository;
import com.cadastro.usuarios.exception.CrudException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Usuario retrieve(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CrudException(HttpStatus.NO_CONTENT, "Não existe usuário na base com este ID"));
    }


    public Usuario save(Usuario user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new CrudException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
        } catch (IllegalArgumentException ex) {
            throw new CrudException(HttpStatus.BAD_REQUEST, "Usuario inválido");
        } catch (Exception ex){
            throw new CrudException(HttpStatus.BAD_REQUEST,"Não foi possivel salvar o usuario");
        }
    }

    public Usuario update(Usuario user) {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new CrudException(HttpStatus.NO_CONTENT, "Usuário inexistente"));
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(()-> new CrudException(HttpStatus.NO_CONTENT,"Usuario não existe"));
            userRepository.deleteById(id);
    }
}
