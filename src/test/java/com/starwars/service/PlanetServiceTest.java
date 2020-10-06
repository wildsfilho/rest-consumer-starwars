package com.starwars.service;

import com.starwars.domain.Planet;
import com.starwars.domain.external.PlanetSwapi;
import com.starwars.domain.external.ResultSwapi;
import com.starwars.repository.PlanetRepository;
import com.starwars.service.translator.PlanetTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class PlanetServiceTest {

    private static final String URL_GET_PLANETS = "https://swapi.dev/api/planets/?page=1";
    private static final String NAME_PLANET = "Tatooine";
    private static final Integer ID = 1;

    @Mock
    private SwapiRequestExecutor requestExecutor;
    @Mock
    private PlanetTranslator translator;
    @Mock
    private PlanetRepository planetRepository;

    @InjectMocks
    private PlanetService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_get_planets_external() {
        ResultSwapi resultSwapi = mock(ResultSwapi.class);
        PlanetSwapi planetSwapi = mock(PlanetSwapi.class);
        when(planetSwapi.getName()).thenReturn(NAME_PLANET);
        when(resultSwapi.getNext()).thenReturn(null);
        List<Object> list = new ArrayList<>();
        list.add(planetSwapi);
        when(resultSwapi.getResults()).thenReturn(list);
        when(requestExecutor.get(URL_GET_PLANETS, ResultSwapi.class)).thenReturn(resultSwapi);
        when(translator.planetSwapiToDto(resultSwapi)).thenReturn(planetSwapi);
        List<PlanetSwapi> planetsExternal = service.getPlanetsExternal();
        assertEquals(1, planetsExternal.size());
    }

    @Test
    void should_get_planets() {
        List<Planet> list = new ArrayList<>();
        Planet planet1 = mock(Planet.class);
        Planet planet2 = mock(Planet.class);
        list.add(planet1);
        list.add(planet2);
        when(planetRepository.findAll()).thenReturn(list);
        List<Planet> planets = service.getPlanets();
        assertEquals(2, planets.size());
        assertEquals(planet1, planets.get(0));
        assertEquals(planet2, planets.get(1));
    }

    @Test
    void should_get_planet_by_name() {

        Planet planet1 = mock(Planet.class);
        when(planet1.getName()).thenReturn(NAME_PLANET);
        when(planetRepository.findByName(NAME_PLANET)).thenReturn(planet1);
        Planet planet = service.getPlanetByName(NAME_PLANET);
        assertEquals(planet1, planet);
        assertEquals(NAME_PLANET, planet.getName());

    }

    @Test
    void should_get_planet_by_id() {
        Planet planet1 = mock(Planet.class);
        when(planet1.getName()).thenReturn(NAME_PLANET);
        when(planetRepository.findById(ID)).thenReturn(Optional.of(planet1));
        Planet planet = service.getPlanetById(ID);
        assertEquals(planet1, planet);
        assertEquals(NAME_PLANET, planet.getName());

    }

    @Test
    void should_get_planet_by_id_not_exist() {
        when(planetRepository.findById(ID)).thenReturn(Optional.ofNullable(null));
        Planet planet = service.getPlanetById(ID);
        assertNull(planet);

    }

    @Test
    void should_delete_b_id() {
        service.deletePlanet(ID);
       verify(planetRepository).deleteById(ID);

    }

    @Test
    void should_create_planet() {
        ResultSwapi resultSwapi = mock(ResultSwapi.class);
        PlanetSwapi planetSwapi = mock(PlanetSwapi.class);
        Planet planet = mock(Planet.class);
        when(planet.getName()).thenReturn(NAME_PLANET);
        when(planetSwapi.getName()).thenReturn(NAME_PLANET);
        when(resultSwapi.getNext()).thenReturn(null);
        List<Object> list = new ArrayList<>();
        list.add(planetSwapi);
        when(resultSwapi.getResults()).thenReturn(list);
        when(requestExecutor.get(URL_GET_PLANETS, ResultSwapi.class)).thenReturn(resultSwapi);
        when(translator.planetSwapiToDto(any())).thenReturn(planetSwapi);
        service.createPlanet(planet);
        verify(planetRepository).save(planet);

    }

}