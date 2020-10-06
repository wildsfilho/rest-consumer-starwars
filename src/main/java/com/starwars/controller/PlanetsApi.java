package com.starwars.controller;

import com.starwars.domain.Planet;
import com.starwars.domain.external.PlanetSwapi;
import com.starwars.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/planets")
public class PlanetsApi {

    @Autowired
    private PlanetService planetService;

    // Adicionar flux depois
    @GetMapping("/get-external")
    public List<PlanetSwapi> getPlanetsExternal() {
        return planetService.getPlanetsExternal();
    }

    @GetMapping("/get")
    public List<Planet> getPlanets() {
        return planetService.getPlanets();
    }

    @GetMapping("/get/{name}")
    public Planet getPlanetsByName(@PathParam("name") String name) {
        return planetService.getPlanetByName(name);
    }

    @GetMapping("/get/{id}")
    public Planet getPlanetsById(@PathParam("id") Integer id) {
        return planetService.getPlanetById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePlanetById(@PathParam("id") Integer id) {
         planetService.deletePlanet(id);
    }

    @PostMapping
    public void createPlanet(@RequestBody Planet planet){
        planetService.createPlanet(planet);
    }
}
