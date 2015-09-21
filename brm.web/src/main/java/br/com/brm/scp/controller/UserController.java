package br.com.brm.scp.controller;

import java.io.Serializable;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("application")
public class UserController implements Serializable {

	private static final long serialVersionUID = -1334399509544544618L;
	
	private static final String FORMAT_DATE = "dd/MM/yyyy";

	@RequestMapping(value = "user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Principal userLogado(Principal user) {
		return user;
	}

	@RequestMapping(value = "date", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String date() {
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
		return format.format(new Date());
	}

}
