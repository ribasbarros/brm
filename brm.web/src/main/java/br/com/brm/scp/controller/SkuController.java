package br.com.brm.scp.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.brm.scp.api.service.SkuService;

@Controller
@RequestMapping("sku")
public class SkuController implements Serializable {
	@Autowired
	SkuService skuService;
	
	private static final long serialVersionUID = 9119085167667772244L;
	
}
