package ru.danilenko.telros.ui.form;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.danilenko.telros.backend.emuns.Roles;
import ru.danilenko.telros.backend.entity.User;
import ru.danilenko.telros.backend.service.UserService;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


public class UserFormTest {
    @Mock
    private UserService userService;
    private User user1;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        user1 = new User();
        user1.setEmail("user1@mail.ru");
        user1.setName("user");
        user1.setSurname("1");
        user1.setPhoneNumber("+79212277000");
        user1.setRole(Roles.USER);
        user1.setFatherName("user 0");
        user1.setDateOfBirth(new Date(2000, Calendar.FEBRUARY,1));
    }

    @Test
    public  void formFieldFilledRead(){
        UserForm userForm = new UserForm(userService);
        userForm.setUser(user1);
        Assertions.assertEquals("user", userForm.name.getValue());
        Assertions.assertEquals("1", userForm.surname.getValue());
        Assertions.assertEquals("user 0", userForm.fatherName.getValue());
        Assertions.assertEquals("+79212277000", userForm.phoneNumber.getValue());
        Assertions.assertEquals(Roles.USER, userForm.role.getValue());
        Assertions.assertEquals("user1@mail.ru", userForm.email.getValue());
    }

    @Test
    public  void formFieldFilledSet(){
        UserForm userForm = new UserForm(userService);
        userForm.name.setValue("user");
        userForm.surname.setValue("1");
        userForm.fatherName.setValue("user 0");
        userForm.phoneNumber.setValue("+79212277000");
        userForm.role.setValue(Roles.USER);
        userForm.email.setValue("user1@mail.ru");
        User user = userForm.getUser();
        Assertions.assertEquals("user", user.getName());
        Assertions.assertEquals("1", user.getSurname());
        Assertions.assertEquals("user 0", user.getFatherName());
        Assertions.assertEquals("+79212277000", user.getPhoneNumber());
        Assertions.assertEquals(Roles.USER, user.getRole());
        Assertions.assertEquals("user1@mail.ru", user.getEmail());
    }

}
