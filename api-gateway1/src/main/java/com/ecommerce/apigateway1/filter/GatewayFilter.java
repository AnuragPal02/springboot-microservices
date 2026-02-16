package com.ecommerce.apigateway1.filter;




import com.ecommerce.apigateway1.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class GatewayFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        // Allow auth endpoints without token
        if (path.startsWith("/auth/")) {
            chain.doFilter(request, response);
            return;
        }

        // Allow GET requests to products without auth
        if (path.startsWith("/products") && method.equals("GET")) {
            chain.doFilter(request, response);
            return;
        }
        // Allow Swagger UI and API docs
        if (path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.equals("/swagger-ui.html")) {
            chain.doFilter(request, response);
            return;
        }

        // For other requests, validate token
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);

        try {
            if (!jwtUtil.validateToken(token)) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Invalid token");
                return;
            }

            // Extract user info and add to headers for downstream services
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            httpRequest.setAttribute("X-User-Email", email);
            httpRequest.setAttribute("X-User-Role", role);

            chain.doFilter(request, response);

        } catch (Exception e) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Token validation failed: " + e.getMessage());
        }
    }
}