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


@Route("/admin")
@RolesAllowed("ROLE_ADMIN")
public class AdminPage extends VerticalLayout {

    private Button addUser = new Button("Добавить пользователя", buttonClickEvent -> UI.getCurrent().getPage().setLocation("/add"));
    private Button getUsers = new Button("Посмотреть пользователей",buttonClickEvent -> UI.getCurrent().getPage().setLocation("/users"));
    private Paragraph greeting = new Paragraph("Вы вошли как админ");
    private LogoutConfig logoutConfig;
    private Button logout = new Button("Выйти",buttonClickEvent -> logoutConfig.logout());


    public AdminPage(LogoutConfig logoutConfig){
        this.logoutConfig = logoutConfig;

        add(
                new H1(greeting),
                new HorizontalLayout(addUser, getUsers),
                logout

        );
    }
}
