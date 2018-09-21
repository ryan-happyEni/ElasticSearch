package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = { "/403", "/400", "/error"})
	public String test() {
		log.debug("index call... / ");
		return "error/error";
	}
	
	@RequestMapping("/404")
	public String index() {
		log.debug("index call htm... / ");
		return "error/404";
	}
	
	@RequestMapping("/accessDenied")
	public String not_allow() {
		log.debug("index call htm... / ");
		return "error/accessDenied";
	}
}
