package ru.akimov.voteapp.rest;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.akimov.voteapp.dao.RestorantRepository;
import ru.akimov.voteapp.domain.Dish;
import ru.akimov.voteapp.domain.Restorant;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

/**
 * Created by z003cptz on 30.11.2015.
 */
@Validated
@RestController
@RequestMapping("/restorants")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RestorantController {

    private final RestorantRepository restorantRepository;

    private final Validator validator;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Restorant> getRestorants() {
        return restorantRepository.findAll();
    }


    @RequestMapping(value = "/{restorantId}/menu", method = RequestMethod.GET)
    public Collection<Dish> getMenu(@PathVariable Long restorantId) {
        Restorant restorant = restorantRepository.findById(restorantId).orElseThrow(() ->
                new NoSuchEntityException(restorantId, "Restorant"));

        return restorant.getMenu();
    }

    @RequestMapping(value = "/{restorantId}/menu", method = {RequestMethod.PUT, RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMenu(@PathVariable @NotNull Long restorantId,
                                        @Valid @NotEmpty @RequestBody Collection<Dish> menu) {
        Restorant restorant = restorantRepository.findById(restorantId).orElseThrow(() ->
                new NoSuchEntityException(restorantId, "Restorant"));

        //TODO: What to do if votes were done for existing menu?

        restorant.setMenu(menu);
        restorant.setMenuUpdateTime(new Date());

        restorantRepository.saveAndFlush(restorant);

        return new ResponseEntity<>(menu, HttpStatus.OK);
    }
}