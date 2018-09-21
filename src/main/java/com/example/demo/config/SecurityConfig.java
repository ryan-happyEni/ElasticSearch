package com.example.demo.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.login.AuthProvider;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    AuthProvider authProvider;

    @Autowired
    ObjectMapper objectMapper;

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/static/**");
	}
	
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()  //POST 전송시 csrf 를 설정하거나 설정하지 않을 땐 disable 시켜야함.
            // 기본 인증 페이지 설정 ("/": all)
            .authorizeRequests()
            	//.antMatchers("/mypage/**").hasRole("USER")
                .antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/not_auth/").permitAll()
                .antMatchers("/elastic").permitAll()
                .antMatchers("/elastic/**").permitAll()
                .antMatchers("/mypage").authenticated()
                .antMatchers("/mypage/**").authenticated()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                //.anyRequest().authenticated()
            // 로그인 페이지 설정 (컨트롤러 mapping이 없을 경우 기본 "/login" 페이지 제공)
            .and()
	            .formLogin()
            	.loginPage("/login")
	            .loginProcessingUrl("/login") //the URL on which the clients should post the login information
	            .usernameParameter("username") //the username parameter in the queryString, default is 'username'
	            .passwordParameter("password") //the password parameter in the queryString, default is 'password'
	            .successHandler(this::loginSuccessHandler)
	            .failureHandler(this::loginFailureHandler)
            
            	/*
            	.loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                // 로그인에 성공했을 경우 redirect URL 설정
                /*
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                            Authentication authentication) throws IOException, ServletException {
                        redirectStrategy.sendRedirect(request, response, "/mail/downloadlist");
                        }
                })
                */
                .permitAll()
            // 로그아웃 설정 ("/logout"을 호출할 경우 로그아웃. 로그아웃이 성공했을 경우 "/login" 페이지로 이동) 
            .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //.logoutSuccessUrl("/login")
                .logoutSuccessUrl("/") 
                .invalidateHttpSession(true)

            .and()
            	//.exceptionHandling().accessDeniedPage("/accessDenied")
        		.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                ;

        http.authenticationProvider(authProvider);
    }
    
    private void loginSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getWriter(), "success");
    }
 
    private void loginFailureHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getWriter(), "fail");
    }
 
    /*
    private void logoutSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getWriter(), "Bye!");
    }
    */
    
    /*
    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        // 기본 인증 계정
        //authManagerBuilder.inMemoryAuthentication().withUser(userProperties.getUsername()).password(userProperties.getPassword()).roles("USER");
    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean 
	@Override 
	public AuthenticationManager authenticationManagerBean() throws Exception { 
		return super.authenticationManagerBean(); 
	}
	*/

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }
}
