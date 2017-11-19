package com.gianfranco.trabajoparcial.dao;

import com.gianfranco.trabajoparcial.TrabajoParcialApplication;
import com.gianfranco.trabajoparcial.domain.Client;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TrabajoParcialApplication.class)
@DatabaseSetup("classpath:test-data.xml")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class ClientDaoTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private HobbyDao hobbyDao;

    private Client client;

    @Before
    public void setUp() {
        client = new Client();
        client.setDni("72192337");
        client.setSex(true);
        client.setLastname("Monzon");
        client.setFirstname("Gianfranco");
        client.setDescription("Description");

        int limaID = 1;
        client.setCity(cityDao.findOne(limaID));

        client.setHobbies(hobbyDao.findAll());
    }

    @Test
    public void findAllReturnsListOfClients() throws Exception {
        int size = clientDao.findAll().size();

        assertThat(size, greaterThan(0));
    }

    @Test
    public void saveClientSucceeds() throws Exception {
        Client newClient = clientDao.save(client);

        assertNotNull(newClient);
    }

    @Test
    public void saveClientsThrowsDataIntegrityViolationExceptionIfDniIsRepeated() throws Exception {
        String repeatedDNI = "72192339";
        client.setDni(repeatedDNI);

        thrown.expect(DataIntegrityViolationException.class);

        Client repeatedClient = clientDao.save(client);

        assertNull(repeatedClient);
    }

    @Test
    public void findByDniReturnsClient() throws Exception {
        String clientDni = "72192339";

        Client client = clientDao.findByDni(clientDni);

        assertNotNull(client);
    }

    @Test
    public void findByDniThrowsClientNotFoundExceptionIfClientIsNotFound() throws Exception {
        String invalidDni= "-1";

        Client invalidClient = clientDao.findByDni(invalidDni);

        assertNull(invalidClient);
    }

    @Test
    public void deleteClientSucceeds() throws Exception {
        int clientID = 1;

        Throwable throwable = catchThrowable(() -> clientDao.delete(clientID));

        assertNull(throwable);
    }

    @Test
    public void deleteClientThrowsEmptyResultDataAccessExceptionIfClientIsNotFound() throws Exception {
        int invalidID = -1;

        thrown.expect(EmptyResultDataAccessException.class);
        clientDao.delete(invalidID);
    }
}
