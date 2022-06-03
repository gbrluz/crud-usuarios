package com.cadastro.usuarios.domain.model;

import com.sun.jdi.LongValue;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioTest {
    @Test
    public void UsuarioTest() {
        Usuario user = new Usuario(10L,"name","sobrenome","email@email.com", 12, true);
        assertEquals(10L, user.getId());
        assertEquals("name", user.getNome());
        assertEquals("sobrenome", user.getSobrenome());
        assertEquals("email@email.com", user.getEmail());
        assertEquals(12, user.getIdade());
        assertTrue(user.isAtivo());
    }
}
