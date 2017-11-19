package com.gianfranco.trabajoparcial.service;

import com.gianfranco.trabajoparcial.dao.ClientDao;
import com.gianfranco.trabajoparcial.domain.Client;
import com.gianfranco.trabajoparcial.service.exception.ClientDeleteException;
import com.gianfranco.trabajoparcial.service.exception.ClientNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.junit.rules.ExpectedException;

public class ClientServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private ClientService clientService = new ClientServiceImpl();

    @Mock
    private ClientDao clientDao;

    private Client client;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        client = new Client();
        client.setId(1);
        client.setDni("72192339");
    }

    @Test
    public void findAllReturnsListOfClients() throws Exception {
        List<Client> clients = Arrays.asList(
                new Client(),
                new Client()
        );

        when(clientDao.findAll()).thenReturn(clients);

        assertThat(clientService.findAll(), is(clients));

        verify(clientDao).findAll();
    }

    @Test
    public void saveClientSucceeds() throws Exception {
        when(clientDao.save(client)).thenReturn(client);

        clientService.save(client);

        verify(clientDao).save(client);
    }

    @Test
    public void saveClientsThrowsDataIntegrityViolationExceptionIfDniIsRepeated() throws Exception {
        String errorMessage = "DNI was repeated";
        when(clientDao.save(client)).thenThrow(new DataIntegrityViolationException(errorMessage));

        thrown.expect(DataIntegrityViolationException.class);
        thrown.expectMessage(errorMessage);
        clientService.save(client);

        verify(clientDao).save(client);
    }

    @Test
    public void findByDniReturnsClient() throws Exception {
        when(clientDao.findByDni(client.getDni())).thenReturn(client);

        assertEquals(clientService.findByDni(client.getDni()), client);

        verify(clientDao).findByDni(client.getDni());
    }

    @Test
    public void findByDniThrowsClientNotFoundExceptionIfClientIsNotFound() throws Exception {
        when(clientDao.findByDni(client.getDni())).thenReturn(null);

        thrown.expect(ClientNotFoundException.class);
        clientService.findByDni(client.getDni());

        verify(clientDao).findByDni(client.getDni());
    }

    @Test
    public void deleteClientSucceeds() throws Exception {
        when(clientDao.findByDni(client.getDni())).thenReturn(client);

        clientService.delete(client.getDni());

        verify(clientDao).findByDni(client.getDni());
    }

    @Test
    public void deleteClientThrowsClientDeleteExceptionIfClientIsNotFound() throws Exception {
        when(clientDao.findByDni(client.getDni())).thenReturn(null);

        thrown.expect(ClientDeleteException.class);
        clientService.delete(client.getDni());

        verify(clientDao).findByDni(client.getDni());
    }
}
