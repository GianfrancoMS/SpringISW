package com.gianfranco.trabajoparcial.dao;

import com.gianfranco.trabajoparcial.domain.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HobbyDao extends JpaRepository<Hobby, Integer> {
}
