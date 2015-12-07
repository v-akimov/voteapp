package ru.akimov.voteapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.akimov.voteapp.domain.Restorant;

import java.util.Optional;

/**
 * Created by z003cptz on 02.12.2015.
 */
@Repository
public interface RestorantRepository extends JpaRepository<Restorant, Long> {

    Optional<Restorant> findById(Long id);
}
