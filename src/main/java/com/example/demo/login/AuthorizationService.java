package com.example.demo.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.repository.User;
import com.example.demo.service.UserService;

@Service
public class AuthorizationService {
	
	@Autowired
	UserService userService;

    public User login(String id, String password) {
    	User user = userService.findByUsername(id);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
    	System.out.println("hashedPassword["+hashedPassword+"]");
    	if(user==null){
    		return null;
    	}else {
    		if(!passwordEncoder.matches(password, user.getPassword() )) { 
    			return null;
    		}
    	}
        return user;
    }

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof MyAuthenticaion)
            return ((MyAuthenticaion) authentication).getUser();
        return null;
    }

    public static void setCurrentUser(User user) {
        ((MyAuthenticaion)
                SecurityContextHolder.getContext().getAuthentication()).setUser(user);
    }

} 
