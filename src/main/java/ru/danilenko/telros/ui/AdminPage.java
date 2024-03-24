package ru.danilenko.telros.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import ru.danilenko.telros.backend.security.LogoutConfig;

/**
 * Admin page view
 */
@Route("/admin")
@RolesAllowed("ROLE_ADMIN")
public class AdminPage extends VerticalLayout {

    /**
     * add new user button with click even listener with redirection to {@link UserAddForm}
     */
    protected Button addUser = new Button("Добавить пользователя", buttonClickEvent -> UI.getCurrent().getPage().setLocation("/add"));
    /**
     * get list of users button with click even listener with redirection to {@link UsersList}
     */
    protected Button getUsers = new Button("Посмотреть пользователей",buttonClickEvent -> UI.getCurrent().getPage().setLocation("/users"));
    private Paragraph greeting = new Paragraph("Вы вошли как админ");
    /**
     * LogoutConfig class for logout configuration {@link  LogoutConfig}
     */
    private LogoutConfig logoutConfig;
    /**
     * logout button with click even listener
     */
    protected Button logout = new Button("Выйти",buttonClickEvent -> logoutConfig.logout());

    public AdminPage(LogoutConfig logoutConfig){
        this.logoutConfig = logoutConfig;
        add(
              new H1(greeting),
              new HorizontalLayout(addUser, getUsers),
              logout
        );
    }
}
