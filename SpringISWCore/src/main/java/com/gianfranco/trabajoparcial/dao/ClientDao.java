package com.gianfranco.trabajoparcial.dao;

import com.gianfranco.trabajoparcial.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDao extends JpaRepository<Client, Integer> {
    Client findByDni(String dni);
}
