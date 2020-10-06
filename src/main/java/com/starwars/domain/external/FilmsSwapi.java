package com.starwars.domain.external;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FilmsSwapi {

    private String title;
    private Integer episodeId;
    private String openingCrawl;
    private String director;
    private String producer;
    private LocalDateTime releaseDate;
    @JsonIgnore
    private List<Object> characters;
    @JsonIgnore
    private List<Object> planets;
    @JsonIgnore
    private List<Object> starships;
    @JsonIgnore
    private List<Object> vehicles;
    @JsonIgnore
    private List<Object> speciesExternals;
    private String created;
    private String edited;
    private String url;
}
