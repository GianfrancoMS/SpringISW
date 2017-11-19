package com.gianfranco.trabajoparcial.service;

import com.gianfranco.trabajoparcial.dao.UserDao;
import com.gianfranco.trabajoparcial.domain.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserDao userDao;

    private User user;

    private String username;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setId(1);
        username = "Gianfranco";
    }

    @Test
    public void findByUsernameReturnsUser() throws Exception {
        when(userDao.findByUsername(username)).thenReturn(user);

        assertEquals(userService.findByUsername(username), user);

        verify(userDao).findByUsername(username);
    }

    @Test
    public void loadByUsernameReturnsUser() throws Exception {
        when(userDao.findByUsername(username)).thenReturn(user);

        assertEquals(userService.loadUserByUsername(username), user);

        verify(userDao).findByUsername(username);
    }

    @Test
    public void loadByUsernameThrowsUsernameNotFoundExceptionIfUsernameIsNotFound() throws Exception {
        when(userDao.findByUsername(username)).thenReturn(null);

        thrown.expect(UsernameNotFoundException.class);
        userService.loadUserByUsername(username);

        verify(userDao).findByUsername(username);
    }
}
