package ru.akimov.voteapp.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.akimov.voteapp.domain.Restorant;
import ru.akimov.voteapp.domain.Role;
import ru.akimov.voteapp.domain.User;

import javax.annotation.PostConstruct;

/**
 * Created by z003cptz on 02.12.2015.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InitialDataLoader {

    private final RestorantRepository restorantRepository;

    private final UserRepository userRepository;

    @PostConstruct
    @Transactional
    protected void loadInitialData() {
        loadUsers();
        loadRestorants();
    }

    private void loadUsers() {
        userRepository.save(new User("admin", "password", Role.ADMIN));
        userRepository.save(new User("user1", "password", Role.USER));
        userRepository.save(new User("user2", "password", Role.USER));
    }

    private void loadRestorants() {
        restorantRepository.save(new Restorant("Metropol"));
        restorantRepository.save(new Restorant("Ugly Coyote"));
    }
}
