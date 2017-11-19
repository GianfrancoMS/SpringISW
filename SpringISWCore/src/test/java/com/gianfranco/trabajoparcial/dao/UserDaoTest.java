package com.gianfranco.trabajoparcial.dao;

import com.gianfranco.trabajoparcial.TrabajoParcialApplication;
import com.gianfranco.trabajoparcial.domain.User;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TrabajoParcialApplication.class)
@DatabaseSetup("classpath:test-data.xml")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void findByUsernameReturnsUser() throws Exception {
        String username = "admin";

        User user = userDao.findByUsername(username);

        assertNotNull(user);
        assertEquals(user.getUsername(), username);
    }
}
