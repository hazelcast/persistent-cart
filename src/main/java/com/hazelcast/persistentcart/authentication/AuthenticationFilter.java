package com.hazelcast.persistentcart.authentication;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest servletRequest = (HttpServletRequest) req;
        HttpSession session = servletRequest.getSession();
        if (session.getAttribute("user") == null) {
            ((HttpServletResponse) resp).sendRedirect("/login");
        } else {
            chain.doFilter(req, resp);
        }
    }
}
