package com.gianfranco.trabajoparcial.dao;

import com.gianfranco.trabajoparcial.TrabajoParcialApplication;
import com.gianfranco.trabajoparcial.domain.City;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TrabajoParcialApplication.class)
@DatabaseSetup("classpath:test-data.xml")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class CityDaoTest {
    @Autowired
    private CityDao cityDao;

    @Test
    public void findAllReturnsListOfCities() throws Exception {
        int size = cityDao.findAll().size();

        assertThat(size, greaterThan(0));
    }

    @Test
    public void findByIdReturnsACity() throws Exception {
        int limaID = 1;
        String limaName = "Lima";

        City lima = cityDao.findOne(limaID);

        assertNotNull(lima);
        assertEquals(lima.getName(), limaName);
    }
}
