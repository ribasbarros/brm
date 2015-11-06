package br.com.brm.scp.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

import br.com.brm.scp.api.service.auth.MongoDBAuthenticationProvider;
import br.com.brm.scp.security.filter.CsrfTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MongoDBAuthenticationProvider authenticationProvider;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// This is here to ensure that the static content (JavaScript, CSS, etc)
		// is accessible from the login page without authentication
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http

		.csrf().and()

		// .httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint).and()
				.addFilterAfter(new CsrfTokenFilter(), CsrfFilter.class)

		// access-denied-page: this is the page users will be
		// redirected to when they try to access protected areas.
		.exceptionHandling().accessDeniedPage("/403").and()

		// The intercept-url configuration is where we specify what roles are
		// allowed access to what areas.
		// We specifically force the connection to https for all the pages,
		// although it could be sufficient
		// just on the login page. The access parameter is where the expressions
		// are used to control which
		// roles can access specific areas. One of the most important things is
		// the order of the intercept-urls,
		// the most catch-all type patterns should at the bottom of the list as
		// the matches are executed
		// in the order they are configured below. So /** (anyRequest()) should
		// always be at the bottom of the list.
				.authorizeRequests().antMatchers("/login*").permitAll()
				.antMatchers("/private/grupo/**").hasAuthority("ADMIN")
				.anyRequest().authenticated()
				.and()

		// This is where we configure our login form.
		// login-page: the page that contains the login screen
		// login-processing-url: this is the URL to which the login form should
		// be submitted
		// default-target-url: the URL to which the user will be redirected if
		// they login successfully
		// authentication-failure-url: the URL to which the user will be
		// redirected if they fail login
		// username-parameter: the name of the request parameter which contains
		// the username
		// password-parameter: the name of the request parameter which contains
		// the password
		// .csrf().disable().exceptionHandling()
				.formLogin().loginPage("/login-brm").permitAll().defaultSuccessUrl("/#/dummy")
				.failureUrl("/login-brm?err=1").loginProcessingUrl("/auth/login_check").permitAll()
				.usernameParameter("username").passwordParameter("password")
				//.successHandler(authenticationSuccessHandler).and()

		/* .csrf().csrfTokenRepository(csrfTokenRepository()).and() */

		// This is where the logout page and process is configured. The
		// logout-url is the URL to send
		// the user to in order to logout, the logout-success-url is where they
		// are taken if the logout
		// is successful, and the delete-cookies and invalidate-session make
		// sure that we clean up after logout
				.and().logout().logoutUrl("/auth/logout").deleteCookies("JSESSIONID", "XSRF-TOKEN").invalidateHttpSession(true)
				// .and()

		// The session management is used to ensure the user only has one
		// session. This isn't
		// compulsory but can add some extra security to your application.
				.and().sessionManagement().invalidSessionUrl("/login-brm?time=1").maximumSessions(1);

	}

}
