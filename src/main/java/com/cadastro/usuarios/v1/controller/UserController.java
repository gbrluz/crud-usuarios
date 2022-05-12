package com.cadastro.usuarios.v1.controller;

import com.cadastro.usuarios.domain.model.Usuario;
import com.cadastro.usuarios.v1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value="/user")
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value="/save")
    public ResponseEntity<Usuario> save(@Valid @RequestBody Usuario user){
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @GetMapping(value="/retrieve/{id}")
    public ResponseEntity<Usuario> retrieve (@PathVariable Long id){
        return new ResponseEntity<>(userService.retrieve(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Usuario> update (@Valid @RequestBody Usuario user){
            return new ResponseEntity<>(userService.update(user), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value="/{id}")
    public void delete (@PathVariable Long id) {
        userService.delete(id);
    }
}
