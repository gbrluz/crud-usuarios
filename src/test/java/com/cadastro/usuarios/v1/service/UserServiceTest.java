package com.cadastro.usuarios.v1.service;

import com.cadastro.usuarios.domain.model.DTO.UsuarioDTO;
import com.cadastro.usuarios.domain.model.User;
import com.cadastro.usuarios.domain.repository.UserRepository;
import com.cadastro.usuarios.exception.CrudException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    public void testFindAllUsuarios() {
        List<User> list = new ArrayList<>();

        list.add(new User(10L, "name", "sobrenome", "email@email.com", 12, true));
        list.add(new User(11L, "name2", "sobrenome2", "email2@email.com", 12, true));
        list.add(new User(12L, "name3", "sobrenome3", "email3@email.com", 12, true));

        when(userRepository.findAll()).thenReturn(list);

        //test
        List<UsuarioDTO> userList = userService.retrieveAll();

        assertEquals(3, userList.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testFindUsuario() {
        User user = new User(10L, "name", "sobrenome", "email@email.com", 12, true);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertEquals(userService.retrieve(user.getId()), user);
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    public void testFindUsuarioException() {
        User user = new User(10L, "name", "sobrenome", "email@email.com", 12, true);

        when(userRepository.findById(user.getId())).thenThrow(new CrudException(HttpStatus.NO_CONTENT, ""));
        assertThrows(CrudException.class, () -> userService.retrieve(user.getId()));

    }


    @Test
    public void testSaveUsuario() {
        User user1 = new User("name", "sobrenome", "email@email.com", 12, true);
        User user2 = new User(10L, "name", "sobrenome", "email@email.com", 12, true);

        when(userRepository.save(user1)).thenReturn(user2);

        assertEquals(userService.save(user1), user2);

        verify(userRepository, times(1)).save(user1);
    }

    @Test
    public void testSaveUsuarioException() {
        User user1 = new User("name", "sobrenome", "email@email.com", 12, true);
        User user2 = new User("name2", "sobrenome2", "email@email.com", 12, true);
        User user3 = new User("name3", "sobrenome3", "email@email.com", 12, true);

        when(userRepository.save(user1)).thenThrow(new DataIntegrityViolationException(""));
        assertThrows(CrudException.class, () -> userService.save(user1));
        when(userRepository.save(user2)).thenThrow(new IllegalArgumentException(""));
        assertThrows(CrudException.class, () -> userService.save(user2));
        when(userRepository.save(user3)).thenThrow(new RuntimeException());
        assertThrows(CrudException.class, () -> userService.save(user3));
    }

    @Test
    public void testUpdateUsuario() {
        User user1 = new User(12L, "name", "sobrenome", "email@email.com", 12, true);
        User user2 = new User(12L, "name2", "sobrenome2", "email@email.com", 12, true);

        when(userRepository.save(user1)).thenReturn(user2);
        when(userRepository.findById(user2.getId())).thenReturn(Optional.of(user2));
        assertEquals(userService.update(user1), user2);
        verify(userRepository, times(1)).save(user1);

    }

    @Test
    public void testUpdateUsuarioException() {
        User user1 = new User(12L, "name", "sobrenome", "email@email.com", 12, true);
        User user2 = new User(12L, "name2", "sobrenome2", "email@email.com", 12, true);

        when(userRepository.findById(user2.getId())).thenThrow(new CrudException(HttpStatus.NO_CONTENT, ""));
        assertThrows(CrudException.class, () -> userService.update(user2));

    }

    @Test
    public void testDelete() throws Exception {
        User user1 = new User(1000L, "name", "sobrenome", "email@email.com", 12, true);

        when(userRepository.findById(1000L)).thenReturn(Optional.of(user1));
        userService.delete(1000L);
        Mockito.verify(userRepository).deleteById(1000L);

    }

    @Test
    public void testDeleteException() throws Exception {
        User user1 = new User(1000L, "name", "sobrenome", "email@email.com", 12, true);

        when(userRepository.findById(user1.getId())).thenReturn(Optional.ofNullable(null));
        assertThrows(CrudException.class, () -> userService.delete(1000L));

    }
}