package br.com.brm.scp.security.filter.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpHeaderProvider {
    void filter(HttpServletRequest request, HttpServletResponse response);
}