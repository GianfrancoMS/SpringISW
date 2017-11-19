package com.gianfranco.trabajoparcial.service;

import com.gianfranco.trabajoparcial.domain.Client;
import com.gianfranco.trabajoparcial.service.exception.ClientDeleteException;
import com.gianfranco.trabajoparcial.service.exception.ClientNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface ClientService {
    List<Client> findAll();

    void save(Client client) throws DataIntegrityViolationException;

    Client findByDni(String clientDni) throws ClientNotFoundException;

    void delete(String clientDni) throws ClientDeleteException;
}
