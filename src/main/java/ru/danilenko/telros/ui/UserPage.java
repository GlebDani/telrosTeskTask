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

/**
 * User page view
 */
@Route("/user")
@RolesAllowed("ROLE_USER")
public class UserPage extends VerticalLayout {

    /**
     * User form to display {@link UserForm}
     */
    private UserForm form;
    /**
     * User service to load user info {@link UserService}
     */
    private UserService userService;

    /**
     * configuration to logout {@link LogoutConfig}
     */
    private LogoutConfig logoutConfig;

    /**
     * button to log out
     */
    protected Button logout = new Button("Выйти", buttonClickEvent -> logoutConfig.logout());

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
