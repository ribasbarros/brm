package br.com.brm.scp.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@RequestMapping(value="/user", method=RequestMethod.GET)
	@ResponseBody
	public Principal user(Principal user) {
		return user;
	}
	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	@ResponseBody
	public String user() {
		return "BLA BLA BLA";
	}

}
