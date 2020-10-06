package com.starwars.service.translator;

import com.google.gson.Gson;
import com.starwars.domain.external.PlanetSwapi;
import org.springframework.stereotype.Component;

@Component
public class PlanetTranslator {

//de - from
//para - to

    public PlanetSwapi planetSwapiToDto(Object object) {
        Gson gs = new Gson();
        String js = gs.toJson(object);
        PlanetSwapi planetSwapi = gs.fromJson(js, PlanetSwapi.class);

        return planetSwapi;

    }
}
