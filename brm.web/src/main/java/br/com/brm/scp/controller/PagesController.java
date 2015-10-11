package br.com.brm.scp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {

	@RequestMapping(value="/")
	public String index() {
		return "index";
	}
	
    @RequestMapping(value="/dummy")
    public String dummy() {
        return "dummy";
    }
     
	@RequestMapping(value = "/company")
	public String company() {
		return "company";
	}
	
	@RequestMapping(value = "/login-brm")
	public String login() {
		return "login-brm";
	}
	
	@RequestMapping(value = "/private/fornecedor/fornecedor-view")
	public String fornecedorView() {
		return "private/fornecedor/fornecedor-view";
	}
	
	@RequestMapping(value = "/template")
	public String templateTest() {
		return "template";
	}
	
}
