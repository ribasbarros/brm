package br.com.brm.scp.controller;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PagesController implements Serializable {

	private static final long serialVersionUID = -5696101938250014714L;
	
	private static final Object PRIVATE_DOMAIN = "private";

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
	
	/**
	 * @param type
	 * @param typePage
	 * @return url destino
	 */
	@RequestMapping(value = "/private/{type}/{typePage}", method = RequestMethod.GET)
	public String doPrivate(@PathVariable("type") String type, @PathVariable("typePage") String typePage) {
		return String.format("%s/%s/%s" , PRIVATE_DOMAIN, type, typePage);
	}
	
	/**
	 * @param type
	 * @param typePage
	 * @return url destino
	 */
	@RequestMapping(value = "/private/{type}/{typePage}/{id}", method = RequestMethod.GET)
	public String doPrivateEdit(@PathVariable("type") String type, @PathVariable("typePage") String typePage, @PathVariable("typePage") String id) {
		return String.format("%s/%s/%s" , PRIVATE_DOMAIN, type, typePage);
	}
	
}
