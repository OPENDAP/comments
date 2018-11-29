package org.opendap.rest.controller;

import org.opendap.beans.Welcome;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {

	private static final String welcomemsg = "Welcome %s!";

	@GetMapping("/welcome/user")
	@ResponseBody
	public Welcome welcomeUser(@RequestParam(name = "name", required = false, defaultValue = "Java Fan") String name) {
		System.out.println("Before calling Welcome ctor");
		return new Welcome(String.format(welcomemsg, name));
	}

}
