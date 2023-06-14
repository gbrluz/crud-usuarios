package com.cadastro.usuarios.v1.controller;

import com.cadastro.usuarios.domain.model.User;
import com.cadastro.usuarios.domain.model.DTO.UsuarioDTO;
import com.cadastro.usuarios.v1.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        UsuarioDTO user = new UsuarioDTO(10L, "nome sobrenome", "email@email.com", 12, null,"matheus",22L);
        UsuarioDTO user2 = new UsuarioDTO(10L, "nome sobrenome2", "email@email.com", 12, null,"matheus",22L);
        UsuarioDTO user3 = new UsuarioDTO(10L, "nome sobrenome3", "email@email.com", 12, null,"matheus",22L);

        //criar mais usuarios
        List<UsuarioDTO> userList = List.of(user, user2, user3);
        when(userService.retrieveAll()).thenReturn(userList);
        mockMvc.perform(get("/user/retrieve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].nomeCompleto", Matchers.is("nome sobrenome")))
                .andExpect(jsonPath("$[1].nomeCompleto", Matchers.is("nome sobrenome2")))
                .andExpect(jsonPath("$[2].nomeCompleto", Matchers.is("nome sobrenome3")))
                .andExpect(jsonPath("$[2].idade", Matchers.is(12)));
        //testar mais campos
    }

    @Test
    public void testRetrieve() throws Exception {
        UsuarioDTO user = new UsuarioDTO(10L, "nome sobrenome", "email@email.com", 12, null,"matheus",22L);

        when(userService.retrieve(anyLong())).thenReturn(user);

        mockMvc.perform(get("/user/retrieve/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nomeCompleto", is("nome sobrenome")))
                .andExpect(jsonPath("$.email", is("email@email.com")))
                .andExpect(jsonPath("$.idade", is(12)));
    }

    @Test
    public void testSave() throws Exception {
        User user = new User(10L, "nome", "sobrenome", "email@email.com", 12, true);
        //O mock só vai performar uma ação, quando o parametro usado for ou identico ou o mesmo
        // 1 é igual a L quando "int L = 1";
        // e 1 é igual a 1
        when(userService.save(ArgumentMatchers.any())).thenReturn(user);

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
        UsuarioDTO user = UsuarioDTO.builder()
                .id(1l)
                .nomeCompleto("Rayven Zambo")
                .email("dhasiudhasiduahdia")
                .idade(22)
                .build();

        when(userService.update(ArgumentMatchers.any())).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user));

        mockMvc.perform(mockRequest).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.nome", is("Rayven Zambo")));
    }

    @Test
    public void testDelete() throws Exception {
        User user = new User(10L, "nome", "sobrenome", "email@email.com", 12, true);

        when(userService.save(user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/user/10")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
    }


}