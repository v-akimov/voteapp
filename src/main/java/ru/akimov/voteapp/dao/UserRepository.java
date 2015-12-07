package ru.akimov.voteapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.akimov.voteapp.domain.User;
import ru.akimov.voteapp.domain.VoteResult;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by z003cptz on 06.12.2015.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT new ru.akimov.voteapp.domain.VoteResult(COUNT(u), u.vote.name) FROM User u JOIN u.vote where u.voteDate >= :from and u.voteDate <= :to GROUP BY u.vote ORDER BY COUNT(u) DESC")
    List<VoteResult> findTopVotedRestorants(@Param("from") Date from, @Param("to") Date to);
}
