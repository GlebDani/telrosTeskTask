package ru.danilenko.telros.backend.service;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.danilenko.telros.backend.emuns.Roles;
import ru.danilenko.telros.backend.entity.User;
import ru.danilenko.telros.backend.repository.UserRepository;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
@Testcontainers
public class UserServiceTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16"
    );

    @BeforeAll
   static   void init(){
        postgres.start();
    }
    @AfterAll
    static   void  destroy(){
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    public User createUser(){
        User user = new User();
        user.setEmail("userTest@mail.ru");
        user.setPassword("11");
        user.setName("user");
        user.setSurname("1");
        user.setPhoneNumber("+79212277000");
        user.setRole(Roles.USER);
        user.setFatherName("user 0");
        user.setDateOfBirth(new Date(2000, Calendar.FEBRUARY,1));
        return  user;
    }

    @Test
    public  void  getUserTest(){

        Assertions.assertTrue(userService.getUser(1).isPresent());

    }

    @Test
    public  void  getAllUserTest(){
        Assertions.assertEquals(5, userService.findAll().size());
    }

    @Test
    public  void  AddUserTest(){
        userService.add(createUser());
        Assertions.assertEquals(createUser().getEmail(), userService.getUser(6).get().getEmail());

    }
    @Test
    public  void  getAllUserTest2(){
        Assertions.assertEquals(5, userService.findAll().size());
    }


}
