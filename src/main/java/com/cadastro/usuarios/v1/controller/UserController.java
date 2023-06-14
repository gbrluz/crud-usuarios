package com.cadastro.usuarios.v1.controller;

import com.cadastro.usuarios.domain.model.User;
import com.cadastro.usuarios.domain.model.DTO.UsuarioDTO;
import com.cadastro.usuarios.v1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/user")
@Controller
@AllArgsConstructor
public class UserController {

    UserService userService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public User save(@Valid @RequestBody User user) {
        return userService.save(user);
    }


    @GetMapping
    public ResponseEntity<List> retrieveAll() {
        return new ResponseEntity<>(userService.retrieveAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/mapper")
    public ResponseEntity<List> retrieveAllMapper() {
        return new ResponseEntity<>(userService.retrieveAllMapper(), HttpStatus.OK);
    }


    @GetMapping(value = "/retrieve/{id}")
    public ResponseEntity<UsuarioDTO> retrieve(@PathVariable Long id) {
        return new ResponseEntity<>(userService.retrieve(id), HttpStatus.OK);
    }

    @GetMapping(value = "/retrievemapper/{id}")
    public ResponseEntity<UsuarioDTO> retrieveMapper(@PathVariable Long id) {
        return new ResponseEntity<>(userService.retrieveMapper(id), HttpStatus.OK);
    }

    @PutMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public UsuarioDTO update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
