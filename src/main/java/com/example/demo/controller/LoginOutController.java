package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginOutController {

	@RequestMapping("/logout")
	public String test() {
		return "logout!!";
	}
}
