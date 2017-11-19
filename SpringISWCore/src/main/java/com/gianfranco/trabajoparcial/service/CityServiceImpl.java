package com.gianfranco.trabajoparcial.service;

import com.gianfranco.trabajoparcial.dao.CityDao;
import com.gianfranco.trabajoparcial.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityDao cityDao;

    @Override
    public List<City> findAll() {
        return cityDao.findAll();
    }

    @Override
    public City findById(int cityId) {
        return cityDao.findOne(cityId);
    }
}
