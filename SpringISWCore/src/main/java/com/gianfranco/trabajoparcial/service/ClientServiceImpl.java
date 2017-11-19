package com.gianfranco.trabajoparcial.service;

import com.gianfranco.trabajoparcial.dao.ClientDao;
import com.gianfranco.trabajoparcial.domain.Client;
import com.gianfranco.trabajoparcial.service.exception.ClientDeleteException;
import com.gianfranco.trabajoparcial.service.exception.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientDao clientDao;

    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Override
    public void save(Client client) {
        clientDao.save(client);
    }

    @Override
    public Client findByDni(String clientDni) {
        Client client = clientDao.findByDni(clientDni);
        if (client == null)
            throw new ClientNotFoundException();
        return client;
    }

    @Override
    public void delete(String clientDni) {
        Client client = clientDao.findByDni(clientDni);
        if (client == null)
            throw new ClientDeleteException();
        clientDao.delete(client.getId());
    }
}
