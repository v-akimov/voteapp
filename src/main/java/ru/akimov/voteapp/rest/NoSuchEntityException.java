package ru.akimov.voteapp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by z003cptz on 06.12.2015.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchEntityException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Failed to find entity [%s] with id [%d]";

    public NoSuchEntityException(Long entityId, String entityName) {
        super(String.format(MESSAGE_TEMPLATE, entityName, entityId));
    }
}
