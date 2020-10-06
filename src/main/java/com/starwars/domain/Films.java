package com.starwars.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "films")
public class Films {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "tittle")
    private String title;

    @ManyToOne
    @JoinColumn(name = "id_planet", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Planet planet;

    public Films(String title, Planet planet) {
        this.title = title;
        this.planet = planet;
    }
}
