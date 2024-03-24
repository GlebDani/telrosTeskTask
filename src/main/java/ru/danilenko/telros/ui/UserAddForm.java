package ru.danilenko.telros.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import ru.danilenko.telros.backend.entity.User;
import ru.danilenko.telros.backend.service.UserService;
import ru.danilenko.telros.ui.form.UserForm;

/**
 * Add new user page view
 */
@Route("/add")
@RolesAllowed("ROLE_ADMIN")
public class UserAddForm extends VerticalLayout {
    /**
     * User form to display {@link UserForm}
     */
    protected UserForm userForm;
    /**
     * User service to save user {@link UserService}
     */
    private UserService userService;

    /**
     * Password field to enter password
     */
    protected PasswordField password = new PasswordField("Пароль");
    /**
     * Password field to repeat password
     */
    protected PasswordField password2 = new PasswordField("Повторите пароль");
    /**
     * button to save new user
     */
    Button save = new Button("Сохранить");
    /**
     * button to leave this page to {@link AdminPage}
     */
    Button close = new Button("Назад", buttonClickEvent ->  UI.getCurrent().getPage().setLocation("/admin"));
    public  UserAddForm(UserService userService){
        this.userService = userService;
        userForm = new UserForm(userService);
        configureForm();
        add(
                new H1("New user"),
                userForm
        );
    }

    /**
     * adding to password fields and button to form from {@link UserForm}
     */
    private void configureForm() {
        userForm.add(
                new HorizontalLayout(password,password2),
                createButton()
        );
    }

    /**
     * save user method
     * local binder checks if passwords are identical and not blank
     * allow to save user if both binder are valid
     */
    private void saveUser(){
        Binder<User> binder = new Binder<>();
        binder.forField(password).withValidator(y -> y.equals(password2.getValue()),"Пароли не совпадают")
                .withValidator(y->!y.isBlank(),"Пароль не должен быть пустым")
                .bind(User::getPassword,User::setPassword);
        binder.validate().notifyBindingValidationStatusHandlers();
        if(userForm.validateToAdd() && binder.isValid() ) {
            User user = userForm.getUser();
            user.setPassword(password.getValue());
            userService.add(user);
            Notification.show("User added");
            UI.getCurrent().getPage().setLocation("/users");
        }
    }
    /**
     * method refresh form
     */
    private  void  close(){
        userForm.setUser(null);
    }

    /**
     * method creates 2 buttons to save user and leave the page
     * @return Component of 2 buttons
     */
    private Component createButton(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addClickShortcut(Key.ENTER);
        save.addClickListener(click -> saveUser());
        close.addClickListener(click-> close());
        return new HorizontalLayout(save, close);
    }
}
