package com.cadastro.usuarios;

import com.cadastro.usuarios.v1.controller.UserController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsuariosApplicationTests {
    @Autowired
    UserController userController;

    @Test
    public void contextLoads() {
        Assertions.assertThat(userController).isNot(null);
    }
}
