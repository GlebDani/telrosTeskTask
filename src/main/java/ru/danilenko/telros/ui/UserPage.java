package ru.danilenko.telros.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.danilenko.telros.backend.security.LogoutConfig;
import ru.danilenko.telros.backend.service.UserService;
import ru.danilenko.telros.ui.form.UserForm;

@Route("/user")
@RolesAllowed("ROLE_USER")
public class UserPage extends VerticalLayout {


    private UserForm form;
    private UserService userService;

    private LogoutConfig logoutConfig;

    private Button logout = new Button("Выйти", buttonClickEvent -> logoutConfig.logout());

    public UserPage(UserService userService1, LogoutConfig logoutConfig) {
        this.userService = userService1;
        this.logoutConfig = logoutConfig;
        this.form = new UserForm(userService1);
        form.disable();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        form.setUser(userService.loadUserByEmail(userDetails.getUsername()).get());
        add(
                form,
                logout
        );
    }
}
