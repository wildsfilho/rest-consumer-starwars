package com.starwars.domain.external;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PeopleSwapi {

    private String name;
    private Long height;
    private Long mass;
    private String hairColor;
    private String skinColor;
    private String eyeColor;
    private String birthYear;
    private String gender;
    private String homeWorld;
    private List<String> films;
    private String created;
    private String edited;
    private String url;
}
