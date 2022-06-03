package com.cadastro.usuarios.v1.controller;

import com.cadastro.usuarios.domain.model.Usuario;
import com.cadastro.usuarios.exception.CrudException;
import com.cadastro.usuarios.v1.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    public void testRetrieveAll() throws Exception {
        Usuario user = new Usuario(10L, "nome", "sobrenome", "email@email.com", 12, true);
        Usuario user2 = new Usuario(10L, "nome2", "sobrenome2", "email@email2.com", 12, true);
        Usuario user3 = new Usuario(10L, "nome3", "sobrenome3", "email@email3.com", 20, true);
        //criar mais usuarios
        List<Usuario> userList = List.of(user, user2, user3);
        Mockito.when(userService.retrieveAll()).thenReturn(userList);
        mockMvc.perform(get("/user/retrieve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].nome", Matchers.is("nome")))
                .andExpect(jsonPath("$[1].nome", Matchers.is("nome2")))
                .andExpect(jsonPath("$[2].nome", Matchers.is("nome3")))
                .andExpect(jsonPath("$[2].sobrenome", Matchers.is("sobrenome3")))
                .andExpect(jsonPath("$[2].idade", Matchers.is(20)));
        //testar mais campos
    }

    @Test
    public void testRetrieve() throws Exception {
        Usuario user = new Usuario(10L, "nome", "sobrenome", "email@email.com", 12, true);

        Mockito.when(userService.retrieve(user.getId())).thenReturn(user);

        mockMvc.perform(get("/user/retrieve/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nome", is("nome")))
                .andExpect(jsonPath("$.sobrenome", is("sobrenome")))
                .andExpect(jsonPath("$.email", is("email@email.com")))
                .andExpect(jsonPath("$.idade", is(12)))
                .andExpect(jsonPath("$.ativo", is(true)));
    }

    @Test
    public void testSave() throws Exception {
        Usuario user = new Usuario(10L, "nome", "sobrenome", "email@email.com", 12, true);


        Mockito.when(userService.save(user)).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/user/save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                //.andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nome").value("nome"))
                .andExpect(jsonPath("$.sobrenome", is("sobrenome")))
                .andExpect(jsonPath("$.email", is("email@email.com")))
                .andExpect(jsonPath("$.idade", is(12)))
                .andExpect(jsonPath("$.ativo", is(true)));
    }

    @Test
    public void testUpdate() throws Exception {
        Usuario user = Usuario.builder()
                .id(1l)
                .nome("Rayven Zambo")
                .sobrenome("Zambo")
                .email("dhasiudhasiduahdia")
                .idade(22)
                .ativo(true)
                .build();

        Mockito.when(userService.retrieve(user.getId())).thenReturn(user);
        Mockito.when(userService.save(user)).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Rayven Zambo")));
    }

    @Test
    public void testDelete() throws Exception {
        Usuario user = new Usuario(10L, "nome", "sobrenome", "email@email.com", 12, true);

        Mockito.when(userService.save(user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/user/10")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
    }


}