package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.message.Messages;

@RestController
public class RestTestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Messages messages;

	@RequestMapping("/not_auth/change_lang")
	public String change_lang() {
		log.debug("test1");
		
		return "Not Auth Test1!!"+messages.get("join01.01");
	}

	@RequestMapping(value = "/not_auth/test1", method = RequestMethod.POST)
	public String not_auth_test1() {
		log.debug("test1");
		
		return "Not Auth Test1 post!!"+messages.get("join01.01");
	}

	@RequestMapping("/not_auth/test2")
	public String not_auth_test2() {
		log.debug("test2");
		return "Not Auth Test2!!";
	}

	@RequestMapping("/not_auth/test3")
	public String not_auth_test3() {
		log.debug("test3");
		return "Not Auth Test2!!";
	}

	@RequestMapping("/auth/test")
	public String auth_test() {
		log.debug("auth test");
		return "Auth Test!!";
	}
}
