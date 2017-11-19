package com.gianfranco.trabajoparcial.service;

import com.gianfranco.trabajoparcial.dao.CityDao;
import com.gianfranco.trabajoparcial.domain.City;
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

public class CityServiceTest {
    @InjectMocks
    private CityService cityService = new CityServiceImpl();

    @Mock
    private CityDao cityDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllReturnsListOfCities() throws Exception {
        List<City> cities = Arrays.asList(
                new City(),
                new City()
        );

        when(cityDao.findAll()).thenReturn(cities);

        assertThat(cityService.findAll(), is(cities));

        verify(cityDao).findAll();
    }

    @Test
    public void findByIdReturnsACity() throws Exception {
        City city = new City();
        city.setId(1);

        when(cityDao.findOne(city.getId())).thenReturn(city);

        assertEquals(cityService.findById(city.getId()), city);

        verify(cityDao).findOne(city.getId());
    }
}
