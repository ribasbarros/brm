package br.com.brm.scp.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import br.com.brm.scp.security.filter.interfaces.HttpHeaderProvider;

@Component
public class CsrfHttpHeaderProvider implements HttpHeaderProvider {
    @Override
    public void filter(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader( "Access-Control-Allow-Headers", "X-CSRF-TOKEN");
    }
}