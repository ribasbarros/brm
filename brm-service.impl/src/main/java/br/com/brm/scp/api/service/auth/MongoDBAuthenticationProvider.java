package br.com.brm.scp.api.service.auth;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.brm.scp.api.service.document.GrupoDocument;
import br.com.brm.scp.api.service.document.PerfilDocument;
import br.com.brm.scp.api.service.document.UsuarioDocument;
import br.com.brm.scp.api.service.repositories.UsuarioRepository;

@Service
public class MongoDBAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private static final String USUARIO_SENHA_INVALIDOS = "Por favor digite um usuário e senha válidos!";
	private static final String LOGIN = "Por favor digite o login";
	private static final String PASSWORD = "Por favor digite a senha";
	@Autowired
	private UsuarioRepository repository;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		if (username.isEmpty()) {
			throw new BadCredentialsException(LOGIN);
		}
		
		 String password = (String) authentication.getCredentials();
		if (!StringUtils.hasText(password)) {
			throw new BadCredentialsException(PASSWORD);
		}

		UsuarioDocument document = repository.findByLogin(username);
		
		if (document == null) {
            throw new BadCredentialsException(USUARIO_SENHA_INVALIDOS);
        }
		
		if (!password.equals(document.getSenha())) {
            throw new BadCredentialsException(USUARIO_SENHA_INVALIDOS);
        }
		
		Collection<GrantedAuthority> roles = new HashSet<>();
		for (GrupoDocument g : document.getGrupos()) {
			for (PerfilDocument p : g.getPerfis()) {
				System.out.println(p.getNome());
				roles.add(new SimpleGrantedAuthority(p.getNome()));
			}
		}

		return new User(document.getLogin(), document.getSenha(), roles);
	}

}
