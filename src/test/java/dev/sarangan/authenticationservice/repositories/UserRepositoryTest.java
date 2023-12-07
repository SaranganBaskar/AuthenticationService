package dev.sarangan.authenticationservice.repositories;

import dev.sarangan.authenticationservice.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
class UserRepositoryTest {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    void save() {
//
//        //region Arrange your data
//        Date date = new Date();
//        User user = new User();
//        user.setFullName("User2FirstNameLastName");
//        user.setEmail("user2@gmail.com");
//        user.setPassword("Welcome@1234");
//        user.setCreatedAt(date);
//        user.setLastUpdatedAt(date);
//        //endregion
//
//        //region Act on your data
//        User savedUser = userRepository.save(user);
//        //endregion
//
//        assertThat(savedUser)
//                .isNotNull()
//                .isEqualTo(user);
//
//    }
//
//    @Test
//    void findByEmail() {
//        //Arrange your data
//        String email = "user2@gmail.com";
//
//        //Act on data
//        User user = userRepository.findByEmail(email);
//
//        //Assert
//        assertThat(user).isNotNull();
//
//    }
}