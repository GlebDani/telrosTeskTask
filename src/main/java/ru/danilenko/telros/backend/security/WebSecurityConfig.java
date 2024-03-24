package ru.danilenko.telros.backend.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import ru.danilenko.telros.backend.security.util.MySimpleUrlAuthenticationSuccessHandler;
import ru.danilenko.telros.backend.service.security.UserDetailsServiceImpl;
import ru.danilenko.telros.ui.LoginView;

@EnableWebMvc
@Configuration
public class WebSecurityConfig  extends VaadinWebSecurity {

    private UserDetailsService userDetailsService;

    /**
     * white list
     */
    private final String[] WHITELIST = new String[]{
            "/icons/**",
            "/images/**",
            "/style**",
            "/frontend/**"
    };

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * configuretion
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        setLoginView(http, LoginView.class);
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(WHITELIST).permitAll()
//                                .anyRequest().denyAll()
                )
                .formLogin(form->form
                        .successHandler(myAuthenticationSuccessHandler())
                )

        ;
        super.configure(http);
    }

    /**
     * Custom the authentication
     * @return
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(getPasswordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return  provider;
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return userDetailsService;
    }

    /**
     * create bean of password encored
     * @return
     */
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * create bean of custom AuthenticationSuccessHandler
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }



}
