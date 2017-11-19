package com.gianfranco.trabajoparcial.service;

import com.gianfranco.trabajoparcial.dao.HobbyDao;
import com.gianfranco.trabajoparcial.domain.Hobby;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HobbyServiceImpl implements HobbyService {
    @Autowired
    private HobbyDao hobbyDao;

    @Override
    public List<Hobby> findAll() {
        return hobbyDao.findAll();
    }

    @Override
    public Hobby findById(int hobbyId) {
        return hobbyDao.findOne(hobbyId);
    }
}
