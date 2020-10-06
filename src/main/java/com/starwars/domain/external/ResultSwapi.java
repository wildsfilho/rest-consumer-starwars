package com.starwars.domain.external;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ResultSwapi {

    private String count;
    private String next;
    private String previous;
    private List<Object> results;
}
