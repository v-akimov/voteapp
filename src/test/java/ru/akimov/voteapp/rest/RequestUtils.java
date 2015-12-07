package ru.akimov.voteapp.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Base64;

/**
 * Created by z003cptz on 06.12.2015.
 */
public class RequestUtils {

    public static HttpHeaders getHeaders(String username, String password) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.AUTHORIZATION,
                "Basic " + new String(Base64.encode((String.format("%s:%s", username, password).getBytes()))));

        return httpHeaders;
    }
}
