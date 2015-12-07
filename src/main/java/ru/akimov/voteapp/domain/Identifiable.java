package ru.akimov.voteapp.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by z003cptz on 03.12.2015.
 */
@MappedSuperclass
public class Identifiable {

    @Id
    @Column(name = "ID")
    @Getter
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
