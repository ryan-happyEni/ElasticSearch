package com.example.demo.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.example.demo.util.Monitor;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private Monitor logger = new Monitor();  
 
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
        	logger.debug("AccessDeniedException = " + exc.getMessage());
        	logger.debug("User: " + auth.getName()+ " attempted to access the protected URL: "+ request.getRequestURI());
        }
 
        response.sendRedirect(request.getContextPath() + "/accessDenied");
    }
}