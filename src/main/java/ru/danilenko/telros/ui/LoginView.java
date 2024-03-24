package ru.danilenko.telros.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * Login page view
 */
@Route("/login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();

    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        login.setI18n(configureLoginForm());
        login.setForgotPasswordButtonVisible(false);
        login.setAction("login");
        add(new H1("Добро пожаловать"), login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }

    private  LoginI18n  configureLoginForm(){
        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Введите свои данные");
        i18nForm.setUsername("Логин");
        i18nForm.setPassword("Пароль");
        i18nForm.setSubmit("Войти");
        i18n.setForm(i18nForm);
        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setPassword("Введите пароль");
        i18nErrorMessage.setUsername("Введите логин");
        i18n.setErrorMessage(i18nErrorMessage);
        return i18n;
    }
}