package com.starwars.service;

import com.starwars.domain.Films;
import com.starwars.domain.Planet;
import com.starwars.domain.external.FilmsSwapi;
import com.starwars.domain.external.PlanetSwapi;
import com.starwars.domain.external.ResultSwapi;
import com.starwars.repository.PlanetRepository;
import com.starwars.service.translator.PlanetTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanetService {

    private static final String URL_GET_PLANETS = "https://swapi.dev/api/planets/?page=1";

    @Autowired
    private SwapiRequestExecutor requestExecutor;
    @Autowired
    private PlanetTranslator translator;
    @Autowired
    private PlanetRepository planetRepository;

// colocar o cache DynamoDB

    public List<PlanetSwapi> getPlanetsExternal() {

        ResultSwapi resultSwapi = requestExecutor.get(URL_GET_PLANETS, ResultSwapi.class);
        List<PlanetSwapi> returnAllPlanets = getReturnAllPlanets(resultSwapi);

        return returnAllPlanets;
    }

    public List<Planet> getPlanets() {
        return planetRepository.findAll();
    }

    public Planet getPlanetByName(String name) {
        return planetRepository.findByName(name);
    }

    public Planet getPlanetById(Integer id) {
        Optional<Planet> planetResult = planetRepository.findById(id);
        if (planetResult.isPresent()) {
            return planetResult.get();
        }
        return null;
    }

    public void deletePlanet(Integer id){
        planetRepository.deleteById(id);
    }

    public void createPlanet(Planet planet) {
        List<PlanetSwapi> planetsExternal = getPlanetsExternal();
        getFilmsForPlanetName(planet, planetsExternal);
        planetRepository.save(planet);
    }

    private void getFilmsForPlanetName(Planet planet, List<PlanetSwapi> planetsExternal) {
        List<Films> films = new ArrayList<>();
        Optional<PlanetSwapi> result = planetsExternal.stream().filter(planetDto -> planetDto.getName().equals(planet.getName())).findAny();
        result.ifPresent(planetDto -> planetDto.getFilms().forEach(filmUrl -> {
            FilmsSwapi filmsSwapi = requestExecutor.get(filmUrl, FilmsSwapi.class);
            Films film = new Films(filmsSwapi.getTitle(), planet);
            films.add(film);
        }));
        planet.setFilms(films);
    }

    private List<PlanetSwapi> getReturnAllPlanets(ResultSwapi resultSwapi) {
        List<PlanetSwapi> list = new ArrayList<>();
        if (Objects.nonNull(resultSwapi.getNext())) {
            String urlGetNextPlanets = resultSwapi.getNext();
            ResultSwapi result = requestExecutor.get(urlGetNextPlanets, ResultSwapi.class);
            getReturnAllPlanets(result);
        }
        list.addAll(getPlanetSwapi(resultSwapi));

        return list;

    }

    private List<PlanetSwapi> getPlanetSwapi(ResultSwapi resultSwap) {
        return resultSwap.getResults().stream()
                .map(translator::planetSwapiToDto).collect(Collectors.toList());
    }
}
