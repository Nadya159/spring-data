package by.javaguru.springdata.integration.model;

import by.javaguru.springdata.integration.annotation.IT;
import by.javaguru.springdata.model.entity.Role;
import by.javaguru.springdata.model.entity.User;
import by.javaguru.springdata.model.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@IT
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void checkQueries() {
        var users = userRepository.findAllBy("a", "ov");
        assertThat(users).hasSize(3);
    }

    @Test
    void checkUpdateRole() {
        var user1 = userRepository.getReferenceById(1L);
        assertSame(Role.ADMIN, user1.getRole());

        var resultCount = userRepository.updateRole(Role.USER, 1L, 5L);
        assertEquals(2, resultCount);

        var theSameUser1 = userRepository.getReferenceById(1L);
        assertSame(Role.USER, theSameUser1.getRole());
    }

    @Test
    void checkFirstTop() {
        var topUser = userRepository.findTopByOrderByIdDesc();
        assertTrue(topUser.isPresent());
        topUser.ifPresent(user -> assertEquals(5L, user.getId()));
    }

    @Test
    void checkSort(){
        var sortBy = Sort.sort(User.class);
        var sort = sortBy.by(User::getFirstname)
                .and(sortBy.by(User::getLastname));

        var sortById = Sort.by("firstname").and(Sort.by("lastname"));
        var allUsers = userRepository.findTop3ByBirthDateBefore(LocalDate.now(), sortById.descending());
        assertThat(allUsers).hasSize(3);
    }
}