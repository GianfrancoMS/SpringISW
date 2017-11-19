package com.gianfranco.trabajoparcial.service;

import com.gianfranco.trabajoparcial.domain.Hobby;

import java.util.List;

public interface HobbyService {
    List<Hobby> findAll();

    Hobby findById(int hobbyId);
}
