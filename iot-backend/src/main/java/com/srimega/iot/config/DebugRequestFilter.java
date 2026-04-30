package com.srimega.iot.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
@Order(1)
public class DebugRequestFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(DebugRequestFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        log.info("[DEBUG] >>> {} {}", request.getMethod(), request.getRequestURI());
        log.info("[DEBUG] Origin: {}", request.getHeader("Origin"));
        log.info("[DEBUG] Authorization: {}", request.getHeader("Authorization") != null ? "present" : "MISSING");
        log.info("[DEBUG] Content-Type: {}", request.getHeader("Content-Type"));

        chain.doFilter(req, res);

        log.info("[DEBUG] <<< {} {} → status {}", request.getMethod(), request.getRequestURI(), response.getStatus());
    }
}
