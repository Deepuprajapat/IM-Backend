package com.realestate.invest.Config.JWT;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The {@code JwtAuthenticationEntryPoint} class handles unauthorized access to protected resources by sending an HTTP 401 Unauthorized response.
 * It also provides a JSON response with an error message.
 * 
 * @author Abhishek Srivastav
 * @version 1.0
 * @since 2023-09-01
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint 
{

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    /**
     * Handles unauthorized access by sending an HTTP 401 Unauthorized response and providing an error message.
     *
     * @param request The HTTP request that caused the authentication failure.
     * @param response The HTTP response to send.
     * @param authException The authentication exception that occurred.
     * @throws IOException If there is an issue writing the response.
     * @throws ServletException If there is an issue handling the servlet request.
     */
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException 
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json"); 
            PrintWriter writer = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Access Denied !! " + authException.getMessage());
            logger.info("Unauthorized : {}",authException.getMessage());
            System.out.println("Error : "+authException.getMessage());
            objectMapper.writeValue(writer, errorResponse);
        }
    
}
