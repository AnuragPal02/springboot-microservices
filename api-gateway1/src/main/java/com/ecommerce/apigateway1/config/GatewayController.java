package com.ecommerce.apigateway1.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.util.Collections;
import java.util.Enumeration;

@RestController
public class GatewayController {

    @Value("${auth.service.url}")
    private String authServiceUrl;

    @Value("${product.service.url}")
    private String productServiceUrl;

    private final RestTemplate restTemplate;

    public GatewayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/auth/**")
    public ResponseEntity<?> routeAuth(HttpServletRequest request) {
        return forwardRequest(request, authServiceUrl);
    }

    @RequestMapping("/products/**")
    public ResponseEntity<?> routeProducts(HttpServletRequest request) {
        return forwardRequest(request, productServiceUrl);
    }

    private ResponseEntity<?> forwardRequest(HttpServletRequest request, String targetUrl) {
        String path = request.getRequestURI();
        String fullUrl = targetUrl + path;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!headerName.equalsIgnoreCase("host") &&
                    !headerName.equalsIgnoreCase("content-length")) {
                headers.put(headerName, Collections.list(request.getHeaders(headerName)));
            }
        }

        // Add user info from attributes (set by filter)
        String userEmail = (String) request.getAttribute("X-User-Email");
        String userRole = (String) request.getAttribute("X-User-Role");

        if (userEmail != null) {
            headers.add("X-User-Email", userEmail);
        }
        if (userRole != null) {
            headers.add("X-User-Role", userRole);
        }

        try {
            StringBuilder bodyBuilder = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                bodyBuilder.append(line);
            }
            String body = bodyBuilder.toString();

            HttpEntity<String> entity = new HttpEntity<>(body.isEmpty() ? null : body, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    fullUrl,
                    HttpMethod.valueOf(request.getMethod()),
                    entity,
                    String.class
            );

            return ResponseEntity.status(response.getStatusCode())
                    .headers(response.getHeaders())
                    .body(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Gateway error: " + e.getMessage());
        }
    }
}