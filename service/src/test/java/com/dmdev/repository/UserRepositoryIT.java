package com.dmdev.repository;

import com.dmdev.entity.Role;
import com.dmdev.entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class UserRepositoryIT extends AbstractRepository {

    UserRepository userRepository = new UserRepository(session);

    @Test
    void save() {
        User hr = buildHr();

        userRepository.save(hr);

        session.flush();
        session.clear();
        Optional<User> actualResult = userRepository.findById(hr.getId());
        assertThat(actualResult)
                .isPresent()
                .contains(hr);
    }

    //  получается такой же как и save ?
//  1. save возвращает тебе сущность с id - достаточно проверить id
//  2. у тебя разные when - а ты их миксуешь и не понятно что вообще тестируешь
    @Test
    void findById() {
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
//        actualResult.ifPresent(user -> user.setVersion(0L));

        assertThat(actualResult)
                .isPresent()
                .get()
                .isEqualTo(hr);

    }

    @Test
    void delete() {
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
        User hr = buildHr();
        User hr2 = buildHrWithName("Melani");
        userRepository.save(hr);
        userRepository.save(hr2);
        session.flush();
        session.clear();

        List<User> actualResult = userRepository.findAll();

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