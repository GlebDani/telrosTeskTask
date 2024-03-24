package ru.danilenko.telros.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
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
 * Page with list of users with possibility of changing
 */
@Route("/users")
@RolesAllowed("ROLE_ADMIN")
public class UsersList extends VerticalLayout {
    /**
     * creating a grid based on User class {@link User}
     */
    private Grid<User> grid = new Grid<>(User.class);
    /**
     * need to read initial user email
     */
    private String email;
    private UserForm form;
    private UserService userService;
    private Dialog dialog;
    private PasswordField password = new PasswordField("Пароль");
    private PasswordField password2 = new PasswordField("Повторите пароль");
    private Button update = new Button("Обновить");
    private Button delete = new Button("Удалить");
    private Button close = new Button("Закрыть");
    private Button change = new Button("Поменять пароль");
    private Button backToMain = new Button("Назад", buttonClickEvent -> UI.getCurrent().getPage().setLocation("/admin"));


    public  UsersList(UserService userService){
        this.userService = userService;
        addClassName("users-list");
        formPassword();
        configureForm();
        configureGrid();



        Div content = new Div(new HorizontalLayout(grid, form));
        content.addClassName("content");
        content.setSizeFull();

        add(
                content,
                dialog,
                backToMain
        );
        closeEditor();
        updateList();

    }

    /**
     * set all users, obtained from user service {@link UserService} to grid
     */
    private void updateList() {
        grid.setItems(userService.findAll());
    }

    /**
     * close the form with user data
     */
    private void closeEditor() {
        if(form.getUser()!=null){
            grid.deselect(form.getUser());
        }
        updateList();
        form.setVisible(false);
        removeClassName("editing");
    }

    /**
     * configuration of form based on {@link UserForm}
     * adding buttons to it
     */
    private void configureForm() {
        form = new UserForm(userService);
        form.setWidth("40em");
        form.add(
                createButton()
        );
    }

    /**
     * configure grid with the exhibited fields of user
     * add select listener
     */
    private void configureGrid() {
        grid.setClassName("users-grid");
        grid.setColumns("email","surname","name","role");
        grid.setItems(userService.findAll());
        grid.setWidth("60em");
        grid.setMinHeight(form.getHeight());
        grid.asSingleSelect().addValueChangeListener(event -> editUser(event.getValue()));
    }

    /**
     * set a selected from grid user to form
     * @param value User
     */
    private void editUser(User value) {
        if(value == null)
            closeEditor();
        else {
            email = value.getEmail();
            form.setUser(value);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    /**
     * create buttons
     * @return Component of buttons
     */
    private Component createButton(){
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        change.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.addClickListener(click->{updateUser();});
        delete.addClickListener(click->{delete(); closeEditor();});
        close.addClickListener(click->closeEditor());
        change.addClickListener(click->{dialog.open();});
        return new VerticalLayout(new HorizontalLayout(update,delete, close), change);
    }

    /**
     * method to update password
     * inner binder check the password identity
     * if binder.ifValid then set password to user and call update password
     */
    private void updatePassword() {
        Binder<User> binder = new Binder<>();
        binder.forField(password).withValidator(y -> y.equals(password2.getValue()),"Пароли не совпадают")
                .withValidator(y->!y.isBlank(),"Пароль не должен быть пустым")
                .bind(User::getPassword,User::setPassword);
        binder.validate().notifyBindingValidationStatusHandlers();
        if(binder.isValid()){
            User user = form.getUser();
            user.setPassword(password.getValue());
            userService.updatePassword(user);
            dialog.close();
            password.setValue("");
            password2.setValue("");
        }
    }

    /**
     * creating a popuped dialog window to change password
     */
    private void formPassword() {
        VerticalLayout popupContent = new VerticalLayout();
        popupContent.add(new HorizontalLayout(password,password2));
        dialog =  new Dialog("Обновление пароля",  popupContent);
        dialog.getFooter().add(new Button("Закрыть",event -> dialog.close()));
        dialog.getFooter().add(new Button("Обновить",event -> updatePassword()));
    }

    /**
     * pass initial email of user as "email"
     */
    private void updateUser(){
        if(form.validateToUpdate(email)) {
            userService.update(form.getUser());
            Notification.show("Пользователь обновлев");
        }
    }

    /**
     * deletes user
     */
    private  void  delete(){
        userService.delete(form.getUser());
        Notification.show("Пользователь удален");
    }


}
