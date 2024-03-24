package ru.danilenko.telros.backend.security.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import java.io.IOException;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public MySimpleUrlAuthenticationSuccessHandler() {

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if(userDetails.getAuthorities().stream().anyMatch(f-> f.getAuthority().equals("ROLE_ADMIN")))
            response.sendRedirect("/admin");
        else
            response.sendRedirect("/user");
    }
}
