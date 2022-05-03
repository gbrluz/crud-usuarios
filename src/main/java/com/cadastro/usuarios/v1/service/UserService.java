package com.cadastro.usuarios.v1.service;

import com.cadastro.usuarios.domain.model.Usuario;
import com.cadastro.usuarios.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Usuario retrieve(Long id){ return userRepository.findById(id).get(); }

    public Usuario save(Usuario user){
        return userRepository.save(user);
    }

    public Usuario update(Usuario user) { return userRepository.save(user);}

    public void delete(Long id) { userRepository.deleteById(id); }




}
