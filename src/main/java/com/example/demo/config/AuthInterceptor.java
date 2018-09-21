package com.example.demo.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.demo.login.MyAuthenticaion;
import com.example.demo.util.Monitor;
import com.example.demo.util.WebUtil;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    private Monitor logger = new Monitor();  

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(ex!=null){
			logger.error(ex);
		}
        
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//WebUtil wutil = new WebUtil();
		//wutil.printRequestParams(request);	
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		WebUtil wutil = new WebUtil();
		wutil.printRequestParams(request);
		
		makeSession(request);
		if(checkAuth(request)) {
			
		}else {
			String url = request.getContextPath()+"/";
			response.sendRedirect(url);  
			return false;
		}	

		return super.preHandle(request, response, "");
	}
	
	public void makeSession(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if(session.getAttribute("SPRING_SECURITY_CONTEXT")!=null && session.getAttribute("session_user")==null) {
			logger.debug("make session call... / ");
			MyAuthenticaion my = (MyAuthenticaion)SecurityContextHolder.getContext().getAuthentication();
			logger.debug("session.."+session.getAttribute("SPRING_SECURITY_CONTEXT"));
			logger.debug("my.."+my);
			logger.debug("username.."+my.getUser().getUsername());
			logger.debug("u type.."+my.getUser().getU_type());
			
			session.setAttribute("session_user", my.getUser());
		}
	}
	
	public boolean checkAuth(HttpServletRequest request) {

		boolean result = true;
		String servlet_call_url = request.getServletPath();
		logger.debug("checkGrant : "+servlet_call_url);
		
		List<String> authUrl = new ArrayList<String>();
		
		
		if(servlet_call_url.startsWith("/mypage/")) {
			/*
        	HttpSession session = request.getSession();
        	Member bean = session.getAttribute("session_user")!=null?(Member)session.getAttribute("session_user"):null;
    		logger.debug("session_user : "+bean);
        	if(bean==null) {
        		result = false;
        	}
        	*/
		}
		
		return result;
	}
}
