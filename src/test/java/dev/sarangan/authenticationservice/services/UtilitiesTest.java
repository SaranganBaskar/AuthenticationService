package dev.sarangan.authenticationservice.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UtilitiesTest {

    @Test
    void generateTokenTest(){
        String tokenTest = Utilities.generateSessionToken(20);
        System.out.println(tokenTest);
        assertThat(tokenTest).isNotNull().isNotEmpty();
    }

}