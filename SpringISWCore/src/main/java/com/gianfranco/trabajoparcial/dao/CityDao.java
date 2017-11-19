package com.gianfranco.trabajoparcial.dao;

import com.gianfranco.trabajoparcial.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDao extends JpaRepository<City, Integer> {
}
