package br.com.brm.scp.fw.helper;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UsuarioLogadoHelper {
	
	public static User getUsuarioLogado(){
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
