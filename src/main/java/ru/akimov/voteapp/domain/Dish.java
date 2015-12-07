package ru.akimov.voteapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Created by z003cptz on 03.12.2015.
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    private Double price;
}
