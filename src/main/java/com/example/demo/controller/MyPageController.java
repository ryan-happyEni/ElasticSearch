package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = { "/"})
	public String test(HttpSession session) {
		log.debug("mypage index call... / ");
		log.debug("session.."+session.getAttribute("SPRING_SECURITY_CONTEXT"));
		log.debug("session.."+session.getAttribute("session_user"));
		
		return "mypage";
	}
}
