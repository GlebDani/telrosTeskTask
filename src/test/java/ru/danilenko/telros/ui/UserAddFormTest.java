package ru.danilenko.telros.ui;

import com.vaadin.testbench.unit.UIUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.danilenko.telros.backend.emuns.Roles;
import ru.danilenko.telros.backend.entity.User;
import ru.danilenko.telros.backend.service.UserService;

import java.util.Calendar;
import java.util.Date;

public class UserAddFormTest{

    @Mock
    private UserService userService;

    private User user1;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        user1 = new User();
        user1.setEmail("usertest@mail.ru");
        user1.setName("user");
        user1.setSurname("1");
        user1.setPhoneNumber("+79212277000");
        user1.setRole(Roles.USER);
        user1.setFatherName("user 0");
        user1.setDateOfBirth(new Date(2000, Calendar.FEBRUARY,1));
    }

    @Test
    public  void passwordCheck(){
        UserAddForm userAddForm = new UserAddForm(userService);
        userAddForm.userForm.setUser(user1);
        userAddForm.password.setValue("1");
        userAddForm.password2.setValue("1");
        Mockito.doThrow(new RuntimeException("valid")).when(userService).add(user1);
        Throwable throwable = Assertions.assertThrows(RuntimeException.class, ()-> {userAddForm.save.click();});
        Assertions.assertEquals("valid",  throwable.getMessage());

    }
    @Test
    public  void passwordCheckInvalid(){
        UserAddForm userAddForm = new UserAddForm(userService);
        userAddForm.userForm.setUser(user1);
        userAddForm.password.setValue("1");
        userAddForm.password2.setValue("2");
       userAddForm.save.click();
       Mockito.verify(userService, Mockito.times(0)).add(user1);


    }
}
