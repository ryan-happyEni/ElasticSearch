package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = { "/", "/ko/", "/en/"})
	public String test() {
		log.debug("index call... / ");
		return "index";
	}
	
	@RequestMapping("/index")
	public String index() {
		log.debug("index call htm... / ");
		return "index";
	}
}
