package com.starwars.repository;

import com.starwars.domain.Planet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanetRepository extends CrudRepository<Planet, Integer> {

    Planet findByName(String name);

    List<Planet> findAll();

}
