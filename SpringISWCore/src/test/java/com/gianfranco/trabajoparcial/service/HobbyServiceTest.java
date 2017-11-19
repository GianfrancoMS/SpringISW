package com.gianfranco.trabajoparcial.service;

import com.gianfranco.trabajoparcial.dao.HobbyDao;
import com.gianfranco.trabajoparcial.domain.Hobby;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class HobbyServiceTest {
    @InjectMocks
    private HobbyService hobbyService = new HobbyServiceImpl();

    @Mock
    private HobbyDao hobbyDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllReturnsListOfHobbies() throws Exception {
        List<Hobby> hobbies = Arrays.asList(
                new Hobby(),
                new Hobby()
        );

        when(hobbyDao.findAll()).thenReturn(hobbies);

        assertThat(hobbyService.findAll(), is(hobbies));

        verify(hobbyDao).findAll();
    }

    @Test
    public void findByIdReturnsAHobby() throws Exception {
        Hobby hobby = new Hobby();
        hobby.setId(1);

        when(hobbyDao.findOne(hobby.getId())).thenReturn(hobby);

        assertEquals(hobbyService.findById(hobby.getId()), hobby);

        verify(hobbyDao).findOne(hobby.getId());
    }
}
