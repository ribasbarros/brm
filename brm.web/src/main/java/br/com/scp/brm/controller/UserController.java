package br.com.scp.brm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
    public void send(@RequestBody Object contact) {
		
    }
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET)
	public String get(){
		return "TESTE";
	}
	
}
