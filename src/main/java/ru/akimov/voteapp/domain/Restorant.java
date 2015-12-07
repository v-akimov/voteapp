package ru.akimov.voteapp.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by z003cptz on 30.11.2015.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "RESTORANT")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Restorant extends Identifiable {

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @ElementCollection
    @CollectionTable(
            name = "MENU",
            joinColumns = @JoinColumn(name = "RESTORANT_ID")
    )
    private Collection<Dish> menu = new LinkedList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MENU_UPDATE_DATE")
    private Date menuUpdateTime;

    public Restorant(String name) {
        this.name = name;
    }
}
