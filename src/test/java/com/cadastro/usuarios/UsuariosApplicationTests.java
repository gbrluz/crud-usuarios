package com.cadastro.usuarios;

import com.cadastro.usuarios.domain.model.Usuario;
import com.cadastro.usuarios.domain.repository.UserRepository;
import com.cadastro.usuarios.v1.controller.UserController;
import com.cadastro.usuarios.v1.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.Entity;
import java.lang.annotation.Annotation;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class UsuariosApplicationTests {
	@Autowired
	UserRepository userRepository;

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setup() {
		userRepository.deleteAll();
	}

	@Test
	public void checkController() {
		boolean hasAnnotation = false;

		Annotation annotation = AnnotationUtils.findAnnotation(UserController.class, Controller.class);
		if (annotation != null) {
			hasAnnotation = true;
		}

		assertTrue(hasAnnotation);
	}

	@Test
	public void checkService() {
		boolean hasAnnotation = false;

		Annotation annotation = AnnotationUtils.findAnnotation(UserService.class, Service.class);
		if (annotation != null) {
			hasAnnotation = true;
		}

		assertTrue(hasAnnotation);
	}

	@Test
	public void checkRepository() {
		boolean hasAnnotation = false;

		Annotation annotation = AnnotationUtils.findAnnotation(UserRepository.class, Repository.class);
		if (annotation != null) {
			hasAnnotation = true;
		}

		assertTrue(hasAnnotation);
	}

	@Test
	public void checkEntity() {
		boolean hasAnnotation = false;

		Annotation annotation = AnnotationUtils.findAnnotation(Usuario.class, Entity.class);
		if (annotation != null) {
			hasAnnotation = true;
		}

		assertTrue(hasAnnotation);
	}

	@Test
	public void testPostUsuario() throws Exception {
		Usuario user = new Usuario(18L,"Foo", "Bar","dashdiaudh@sahdaiusd.com",44,true);
		ObjectMapper objectMapper = new ObjectMapper();

		MockHttpServletResponse response = mockMvc.perform(post("/user/save")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(user)))
				.andDo(print())
				.andExpect(jsonPath("$.*", hasSize(6)))
				.andExpect(jsonPath("$.id", greaterThan(0)))
				.andExpect(jsonPath("$.nome").value("Foo"))
				.andExpect(jsonPath("$.sobrenome").value("Bar"))
				.andExpect(jsonPath("$.email").value("dashdiaudh@sahdaiusd.com"))
				.andExpect(jsonPath("$.idade").value(44))
				.andExpect(jsonPath("$.ativo").value(true))
				.andExpect(status().isCreated()).andReturn().getResponse();

		Integer id = JsonPath.parse(response.getContentAsString()).read("$.id");
		assertEquals(true, userRepository.findById(id.longValue()).isPresent());

	}

	@Test
	public void testRetrievePerson() throws Exception {
		Usuario user = new Usuario(18L,"Foo", "Bar","dashdiaudh@sahdaiusd.com",44,true);
		ObjectMapper objectMapper = new ObjectMapper();

		MockHttpServletResponse response = mockMvc.perform(post("/user/save")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(user)))
				.andDo(print())
				.andExpect(jsonPath("$.*", hasSize(6)))
				.andExpect(jsonPath("$.id", greaterThan(0)))
				.andExpect(jsonPath("$.nome").value("Foo"))
				.andExpect(jsonPath("$.sobrenome").value("Bar"))
				.andExpect(jsonPath("$.email").value("dashdiaudh@sahdaiusd.com"))
				.andExpect(jsonPath("$.idade").value(44))
				.andExpect(jsonPath("$.ativo").value(true))
				.andExpect(status().isCreated()).andReturn().getResponse();
		Integer id = JsonPath.parse(response.getContentAsString()).read("$.id");

		mockMvc.perform(get("/user/retrieve/" + id))
				.andDo(print())
				.andExpect(jsonPath("$.*", hasSize(6)))
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.nome").value("Foo"))
				.andExpect(jsonPath("$.sobrenome").value("Bar"))
				.andExpect(jsonPath("$.email").value("dashdiaudh@sahdaiusd.com"))
				.andExpect(jsonPath("$.idade").value(44))
				.andExpect(jsonPath("$.ativo").value(true))
				.andExpect(status().isOk());

	}

}
