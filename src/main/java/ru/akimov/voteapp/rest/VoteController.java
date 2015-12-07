package ru.akimov.voteapp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.akimov.voteapp.dao.RestorantRepository;
import ru.akimov.voteapp.dao.UserRepository;
import ru.akimov.voteapp.domain.User;
import ru.akimov.voteapp.domain.VoteResult;
import ru.akimov.voteapp.util.DateTimeUtils;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

/**
 * Created by z003cptz on 06.12.2015.
 */
@RestController
@RequestMapping("/votes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VoteController {
    private static final int VOTE_DEADLINE_HOUR = 11;

    private final UserRepository userRepository;

    private final RestorantRepository restorantRepository;

    @RequestMapping(value = "/restorants", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity vote(@NotNull @RequestBody Long restorantId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (DateTimeUtils.getCurrentHours() >= VOTE_DEADLINE_HOUR) {
            throw new BadRequestException(String.format("No votes are allowed after %d hours", VOTE_DEADLINE_HOUR));
        }

        currentUser.setVote(restorantRepository.findById(restorantId).orElseThrow(() ->
                new NoSuchEntityException(restorantId, "Restorant")));
        currentUser.setVoteDate(new Date());

        userRepository.save(currentUser);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/restorants", method = RequestMethod.GET)
    public Collection<VoteResult> getTopRestorant() {
        Date startDate = new Date(Instant.now().truncatedTo(ChronoUnit.DAYS).toEpochMilli());

        return userRepository.findTopVotedRestorants(startDate, new Date());
    }
}
