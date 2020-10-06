package com.starwars.controller;

import com.starwars.domain.Films;
import com.starwars.domain.Planet;
import com.starwars.domain.external.PlanetSwapi;
import com.starwars.service.PlanetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class PlanetsApiTest {

    private static final String NAME_PLANET = "Tatooine";
    private static final Integer ID = 1;

    @Mock
    private PlanetService planetService;

    @InjectMocks
    private PlanetsApi api;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_get_planets_external() {
        PlanetSwapi planetSwapi1 = mock(PlanetSwapi.class);
        when(planetSwapi1.getName()).thenReturn(NAME_PLANET);
        when(planetService.getPlanetsExternal()).thenReturn(Arrays.asList(planetSwapi1));
        List<PlanetSwapi> planetsExternal = api.getPlanetsExternal();
        verify(planetService, times(1)).getPlanetsExternal();
        Assertions.assertEquals(1, planetsExternal.size());
        Assertions.assertEquals(NAME_PLANET, planetsExternal.get(0).getName());
    }

    @Test
    public void should_get_planets_external_is_empty() {
        when(planetService.getPlanetsExternal()).thenReturn(Collections.emptyList());
        List<PlanetSwapi> planetsExternal = api.getPlanetsExternal();
        Assertions.assertEquals(0, planetsExternal.size());
    }

    @Test
    public void should_get_planets() {
        Planet planet = mock(Planet.class);
        Films films = mock(Films.class);
        when(planet.getName()).thenReturn(NAME_PLANET);
        when(planet.getFilms()).thenReturn(Arrays.asList(films));
        when(planetService.getPlanets()).thenReturn(Arrays.asList(planet));
        List<Planet> planets = api.getPlanets();
        verify(planetService, times(1)).getPlanets();
        Assertions.assertEquals(1, planets.size());
        Assertions.assertEquals(1, planets.get(0).getFilms().size());
        Assertions.assertEquals(NAME_PLANET, planets.get(0).getName());
    }

    @Test
    public void should_get_planets_not_exist_in_films() {
        Planet planet = mock(Planet.class);
        when(planet.getName()).thenReturn(NAME_PLANET);
        when(planetService.getPlanets()).thenReturn(Arrays.asList(planet));
        List<Planet> planets = api.getPlanets();
        verify(planetService, times(1)).getPlanets();
        Assertions.assertEquals(1, planets.size());
        Assertions.assertEquals(0, planets.get(0).getFilms().size());
        Assertions.assertEquals(NAME_PLANET, planets.get(0).getName());
    }

    @Test
    public void should_get_planets_is_empty() {
        when(planetService.getPlanets()).thenReturn(Collections.emptyList());
        List<Planet> planets = api.getPlanets();
        verify(planetService, times(1)).getPlanets();
        Assertions.assertEquals(0, planets.size());
    }

    @Test
    public void should_get_planet_by_name() {
        Planet planetMock = mock(Planet.class);
        Films filmsMock = mock(Films.class);
        when(planetMock.getName()).thenReturn(NAME_PLANET);
        when(planetMock.getFilms()).thenReturn(Arrays.asList(filmsMock));
        when(planetService.getPlanetByName(NAME_PLANET)).thenReturn(planetMock);
        Planet planet = api.getPlanetsByName(NAME_PLANET);
        verify(planetService, times(1)).getPlanetByName(NAME_PLANET);
        Assertions.assertEquals(planetMock, planet);
        Assertions.assertEquals(planetMock.getName(), planet.getName());
        Assertions.assertEquals(1, planet.getFilms().size());
        Assertions.assertEquals(filmsMock, planet.getFilms().get(0));
    }

    @Test
    public void should_get_planet_by_name_not_contains_films() {
        Planet planetMock = mock(Planet.class);
        when(planetMock.getName()).thenReturn(NAME_PLANET);
        when(planetService.getPlanetByName(NAME_PLANET)).thenReturn(planetMock);
        Planet planet = api.getPlanetsByName(NAME_PLANET);
        verify(planetService, times(1)).getPlanetByName(NAME_PLANET);
        Assertions.assertEquals(planetMock, planet);
        Assertions.assertEquals(planetMock.getName(), planet.getName());
    }

    @Test
    public void should_get_planet_by_name_not_exist() {
        when(planetService.getPlanetByName(NAME_PLANET)).thenReturn(null);
        Planet planet = api.getPlanetsByName(NAME_PLANET);
        verify(planetService, times(1)).getPlanetByName(NAME_PLANET);
        Assertions.assertNull(planet);
    }

    @Test
    public void should_get_planet_by_id() {
        Planet planetMock = mock(Planet.class);
        Films filmsMock = mock(Films.class);
        when(planetMock.getName()).thenReturn(NAME_PLANET);
        when(planetMock.getFilms()).thenReturn(Arrays.asList(filmsMock));
        when(planetService.getPlanetById(ID)).thenReturn(planetMock);
        Planet planet = api.getPlanetsById(ID);
        verify(planetService, times(1)).getPlanetById(ID);
        Assertions.assertEquals(planetMock, planet);
        Assertions.assertEquals(planetMock.getName(), planet.getName());
        Assertions.assertEquals(1, planet.getFilms().size());
        Assertions.assertEquals(filmsMock, planet.getFilms().get(0));
    }

    @Test
    public void should_get_planet_by_id_not_contains_films() {
        Planet planetMock = mock(Planet.class);
        when(planetMock.getName()).thenReturn(NAME_PLANET);
        when(planetService.getPlanetById(ID)).thenReturn(planetMock);
        Planet planet = api.getPlanetsById(ID);
        verify(planetService, times(1)).getPlanetById(ID);
        Assertions.assertEquals(planetMock, planet);
        Assertions.assertEquals(planetMock.getName(), planet.getName());
    }

    @Test
    public void should_get_planet_by_id_not_exist() {
        when(planetService.getPlanetById(ID)).thenReturn(null);
        Planet planet = api.getPlanetsById(ID);
        verify(planetService, times(1)).getPlanetById(ID);
        Assertions.assertNull(planet);
    }

    @Test
    public void should_create_planet() {
        Planet planetMock = mock(Planet.class);
        Films filmsMock = mock(Films.class);
        when(planetMock.getName()).thenReturn(NAME_PLANET);
        when(planetMock.getFilms()).thenReturn(Arrays.asList(filmsMock));
        api.createPlanet(planetMock);
        verify(planetService, times(1)).createPlanet(planetMock);
    }

    @Test
    public void should_create_planet_not_contains_planet() {
        Planet planetMock = mock(Planet.class);
        api.createPlanet(null);
        verify(planetService, times(0)).createPlanet(planetMock);
    }

    @Test
    public void should_delete_planet() {
        api.deletePlanetById(ID);
        verify(planetService, times(1)).deletePlanet(ID);
    }
}