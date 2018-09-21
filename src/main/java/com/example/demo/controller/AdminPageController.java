package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = { "/"})
	public String test() {
		log.debug("admin index call... / ");
		return "admin";
	}
}
