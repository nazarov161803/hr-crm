package com.dmdev.dao;

import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class UserRepositoryIT extends AbstractRepository {

    //    не понимаю как сделать правильно тк session пока еще тут null, но иницилизировать в каждом методе не хочется
//    UserRepository userRepository = new UserRepository(session);
    UserRepository userRepository;

    @Test
    void save() {
        userRepository = new UserRepository(session);
        User hr = buildHr();
        userRepository.save(hr);
        session.flush();
        session.clear();

        final Optional<User> actualResult = userRepository.findById(hr.getId());

        assertThat(actualResult)
                .isPresent()
                .contains(hr);
    }

    //  получается такой же как и save ?
    @Test
    void findById() {
        userRepository = new UserRepository(session);
        User hr = buildHr();
        userRepository.save(hr);
        session.flush();
        session.clear();

        Optional<User> actualResult = userRepository.findById(hr.getId());

        assertThat(actualResult)
                .isPresent()
                .contains(hr);
    }

    @Test
    void update() {
        userRepository = new UserRepository(session);
        User hr = buildHr();
        userRepository.save(hr);
        session.flush();
        session.clear();
        hr.setFirstName("Melani");
        hr.setEmail("test@email.com");
        userRepository.update(hr);
        session.flush();
        session.clear();

        Optional<User> actualResult = userRepository.findById(hr.getId());

        assertThat(actualResult)
                .isPresent()
                .contains(hr);
    }

    @Test
    void delete() {
        userRepository = new UserRepository(session);
        User hr = buildHr();
        userRepository.save(hr);
        session.flush();
        session.clear();
        userRepository.delete(hr);
        session.clear();

        Optional<User> actualResult = userRepository.findById(hr.getId());

        assertThat(actualResult).isNotPresent();
    }

    @Test
    void findAll() {
        userRepository = new UserRepository(session);
        User hr = buildHr();
        User hr2 = buildHrWithName("Melani");
        userRepository.save(hr);
        userRepository.save(hr2);
        session.flush();
        session.clear();

        final List<User> actualResult = userRepository.findAll();

        assertThat(actualResult).hasSize(2)
                .contains(hr)
                .contains(hr2);

    }

    private User buildHr() {
        return User.builder()
                .email("User@gmail1.com")
                .password("12345")
                .firstName("Donald")
                .lastName("Trump")
                .role(Role.HR)
                .build();
    }

    private User buildHrWithName(String firstName) {
        return User.builder()
                .email("User@gmail1.com")
                .password("12345")
                .firstName(firstName)
                .lastName("Trump")
                .role(Role.HR)
                .build();
    }

}