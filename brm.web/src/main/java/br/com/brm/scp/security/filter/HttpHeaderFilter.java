package br.com.brm.scp.security.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.brm.scp.security.filter.interfaces.HttpHeaderProvider;

@Component("httpHeaderFilter")
public class HttpHeaderFilter extends OncePerRequestFilter {
	
    @Autowired
    private List<HttpHeaderProvider> providerList;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        providerList.forEach(e -> e.filter(request, response));

        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.OK.value());
        }
        else {
            filterChain.doFilter(request, response);
        }
    }
}