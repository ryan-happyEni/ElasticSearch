package com.example.demo.login;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.example.demo.repository.User;

import lombok.Data;

@Data
public class MyAuthenticaion extends UsernamePasswordAuthenticationToken {
    private  static final long serialVersionUID = 1L;

    User user;

    public MyAuthenticaion (String id, String password, List<GrantedAuthority> grantedAuthorityList, User user) {
        super(id, password, grantedAuthorityList);
        this.user = user;
    } 
}