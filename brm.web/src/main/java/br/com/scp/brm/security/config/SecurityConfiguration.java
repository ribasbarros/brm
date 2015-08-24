package br.com.scp.brm.security.config;

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

import br.com.scp.brm.security.filter.CsrfTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// The authentication provider below uses MongoDB to store SHA1 hashed
		// passwords
		// To see how to configure users for the example below, please see the
		// README file
		// auth.userDetailsService(loginService).passwordEncoder(new
		// ShaPasswordEncoder());
		auth.inMemoryAuthentication()
		//.passwordEncoder( new ShaPasswordEncoder())
		.withUser("user").password("user").roles("ADMIN");

		// The authentication provider below is the simplest provider you can
		// use
		// The users, their passwords and roles are all added as clear text
		/*
		 * auth.inMemoryAuthentication().withUser("admin").password("admin").
		 * roles("ADMIN").and().withUser("user")
		 * .password("user").roles("USER");
		 */

		// The authentication provider below hashes incoming passwords using
		// SHA1
		// The users passwords below are hashed using SHA1 (see README for
		// values)
		// auth
		// .inMemoryAuthentication()
		// .passwordEncoder( new ShaPasswordEncoder() )
		// .withUser( "admin" )
		// .password( "d033e22ae348aeb5660fc2140aec35850c4da997" )
		// .roles( "ADMIN" )
		// .and()
		// .withUser( "user" )
		// .password( "12dea96fec20593566ab75692c9949596833adc9" )
		// .roles( "USER" );

		// The authentication provider below uses JDBC to retrieve your
		// credentials
		// The data source bean configuration can be found at the bottom of this
		// file
		// The first example uses the default Spring Security tables, see link
		// below
		// http://docs.spring.io/spring-security/site/docs/3.0.x/reference/appendix-schema.html
		// auth
		// .jdbcAuthentication()
		// .dataSource( dataSource )
		// .passwordEncoder( new ShaPasswordEncoder() );

		// The second example shows how you can override the default queries in
		// order
		// to use your own tables rather than Spring Security's default tables
		// The SQL is relatively simple and should be easy to figure out and map
		// to your needs
		// auth
		// .jdbcAuthentication()
		// .dataSource( dataSource )
		// .usersByUsernameQuery( "select username,password from users where
		// username=?" )
		// .authoritiesByUsernameQuery( "select u.username, r.authority from
		// users u, roles r where u.userid = r.userid and u.username =?" );
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
				.authorizeRequests().antMatchers("/login*", "/auth/*").permitAll().antMatchers("/**").authenticated()
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
