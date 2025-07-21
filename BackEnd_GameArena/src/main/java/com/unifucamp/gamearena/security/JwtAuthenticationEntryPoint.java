package com.unifucamp.gamearena.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.setContentType("application/json;charset=UTF-8");

        // Você pode personalizar a mensagem aqui, por exemplo, usando JSON
        String json = """
            {
                "message": "%s"
            }
            """.formatted(authException.getMessage());

        response.getWriter().write(json);
    }
}
