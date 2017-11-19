package com.gianfranco.trabajoparcial.web.controller;

import com.gianfranco.trabajoparcial.domain.City;
import com.gianfranco.trabajoparcial.domain.Client;
import com.gianfranco.trabajoparcial.domain.Hobby;
import com.gianfranco.trabajoparcial.service.CityService;
import com.gianfranco.trabajoparcial.service.ClientService;
import com.gianfranco.trabajoparcial.service.HobbyService;
import com.gianfranco.trabajoparcial.service.exception.ClientDeleteException;
import com.gianfranco.trabajoparcial.service.exception.ClientNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ClientControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private ClientController clientController;

    @Mock
    private CityService cityService;

    @Mock
    private ClientService clientService;

    @Mock
    private HobbyService hobbyService;

    private Client client;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
        client = new Client();
        client.setId(1);
        client.setDni("72192339");
        client.setFirstname("Gianfranco");
        client.setLastname("Monzon");
        client.setDescription("Description");
        client.setCity(new City());
        client.setHobbies(Arrays.asList(
                new Hobby(),
                new Hobby()
        ));
        client.setSex(true);
    }

    @Test
    public void indexShouldIncludeClients() throws Exception {
        List<Client> clients = Arrays.asList(
                new Client(),
                new Client()
        );
        when(clientService.findAll()).thenReturn(clients);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"))
                .andExpect(model().attribute("clients", clients));

        verify(clientService).findAll();
    }

    @Test
    public void addClientShouldIncludeServices() throws Exception {
        List<City> cities = Arrays.asList(
                new City(),
                new City()
        );
        when(cityService.findAll()).thenReturn(cities);

        List<Hobby> hobbies = Arrays.asList(
                new Hobby(),
                new Hobby()
        );
        when(hobbyService.findAll()).thenReturn(hobbies);

        mockMvc.perform(get("/clients/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("form"))
                .andExpect(model().attributeExists("client"))
                .andExpect(model().attribute("cities", cities))
                .andExpect(model().attribute("hobbies", hobbies));

        verify(cityService).findAll();
        verify(hobbyService).findAll();
    }

    @Test
    public void addClientShouldRedirectToDashboard() throws Exception {
        doNothing().when(clientService).save(client);

        mockMvc.perform(postForm("/clients/add", client))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"))
                .andExpect(view().name("redirect:/"))
                .andExpect(flash().attribute(ClientController.FLASH_OK, ClientController.CLIENT_ADDED));
    }

    @Test
    public void addClientShouldRedirectToFormIfThereAreErrors() throws Exception {
        doNothing().when(clientService).save(client);

        client.setHobbies(new ArrayList<>());

        mockMvc.perform(postForm("/clients/add", client))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/clients/add"))
                .andExpect(view().name("redirect:/clients/add"))
                .andExpect(flash().attributeExists("client"))
                .andExpect(flash().attribute(ClientController.FLASH_ERROR, ClientController.ERROR_FORM));
    }

    @Test
    public void addClientShouldRedirectToFormIfDniIsRepeated() throws Exception {
        doThrow(new DataIntegrityViolationException(ClientController.DUPLICATED_DNI)).when(clientService).save(any(Client.class));

        mockMvc.perform(postForm("/clients/add", client))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/clients/add"))
                .andExpect(view().name("redirect:/clients/add"))
                .andExpect(flash().attributeExists("client"))
                .andExpect(flash().attribute(ClientController.FLASH_ERROR, ClientController.DUPLICATED_DNI));

    }

    @Test
    public void updateClientShouldIncludeServices() throws Exception {
        List<City> cities = Arrays.asList(
                new City(),
                new City()
        );
        when(cityService.findAll()).thenReturn(cities);

        List<Hobby> hobbies = Arrays.asList(
                new Hobby(),
                new Hobby()
        );
        when(hobbyService.findAll()).thenReturn(hobbies);

        when(clientService.findByDni(client.getDni())).thenReturn(client);

        mockMvc.perform(get("/clients/edit/" + client.getDni()))
                .andExpect(status().isOk())
                .andExpect(view().name("form"))
                .andExpect(model().attribute("client", client))
                .andExpect(model().attribute("cities", cities))
                .andExpect(model().attribute("hobbies", hobbies));

        verify(cityService).findAll();
        verify(hobbyService).findAll();
        verify(clientService).findByDni(client.getDni());
    }

    @Test
    public void updateClientShouldRedirectToDashboard() throws Exception {
        doNothing().when(clientService).save(client);

        mockMvc.perform(postForm("/clients/edit", client))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"))
                .andExpect(view().name("redirect:/"))
                .andExpect(flash().attribute(ClientController.FLASH_OK, ClientController.CLIENT_UPDATED));
    }

    @Test
    public void updateClientShouldRedirectToFormIfThereAreErrors() throws Exception {
        doNothing().when(clientService).save(client);

        client.setHobbies(new ArrayList<>());

        mockMvc.perform(postForm("/clients/edit", client))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/clients/edit/" + client.getDni()))
                .andExpect(view().name("redirect:/clients/edit/" + client.getDni()))
                .andExpect(flash().attributeExists("client"))
                .andExpect(flash().attribute(ClientController.FLASH_ERROR, ClientController.ERROR_FORM));
    }

    @Test
    public void updateClientShouldRedirectToFormInfDniIsRepeated() throws Exception {
        doThrow(new DataIntegrityViolationException(ClientController.DUPLICATED_DNI)).when(clientService).save(any(Client.class));

        mockMvc.perform(postForm("/clients/edit", client))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/clients/edit/" + client.getDni()))
                .andExpect(view().name("redirect:/clients/edit/" + client.getDni()))
                .andExpect(flash().attributeExists("client"))
                .andExpect(flash().attribute(ClientController.FLASH_ERROR, ClientController.DUPLICATED_DNI));
    }

    @Test
    public void findClientShouldThrowClientNotFoundExceptionIfNotFound() throws Exception {
        when(clientService.findByDni(client.getDni())).thenThrow(new ClientNotFoundException());

        mockMvc.perform(get("/clients/edit/" + client.getDni()))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists(ClientController.FLASH_ERROR));
    }

    @Test
    public void deleteClientShouldRedirectToDashboard() throws Exception {
        doNothing().when(clientService).delete(client.getDni());

        mockMvc.perform(get("/clients/delete/" + client.getDni()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"))
                .andExpect(view().name("redirect:/"))
                .andExpect(flash().attribute(ClientController.FLASH_OK, ClientController.CLIENT_DELETED));
    }

    @Test
    public void deleteClientShouldThrowClientDeleteExceptionIfNotFound() throws Exception {
        doThrow(new ClientDeleteException()).when(clientService).delete(client.getDni());

        mockMvc.perform(get("/clients/delete/" + client.getDni()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/"))
                .andExpect(view().name("redirect:/"))
                .andExpect(flash().attributeExists(ClientController.FLASH_ERROR));
    }
}
