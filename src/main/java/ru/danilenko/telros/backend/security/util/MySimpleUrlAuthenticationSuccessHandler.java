package ru.danilenko.telros.backend.security.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import ru.danilenko.telros.backend.service.security.AuthInfoService;

import java.io.IOException;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private AuthInfoService authInfoService;

    public MySimpleUrlAuthenticationSuccessHandler(AuthInfoService authInfoService) {
        this.authInfoService = authInfoService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("and here");
        authInfoService.save(userDetails.getUsername());
        if(userDetails.getAuthorities().stream().anyMatch(f-> f.getAuthority().equals("ROLE_ADMIN")))
            response.sendRedirect("/admin");
        else
            response.sendRedirect("/user");
    }
}
