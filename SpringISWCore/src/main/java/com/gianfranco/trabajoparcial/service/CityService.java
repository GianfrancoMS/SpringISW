package com.gianfranco.trabajoparcial.service;

import com.gianfranco.trabajoparcial.domain.City;

import java.util.List;

public interface CityService {
    List<City> findAll();

    City findById(int cityId);
}
