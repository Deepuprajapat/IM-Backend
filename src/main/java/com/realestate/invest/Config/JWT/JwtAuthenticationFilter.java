package com.realestate.invest.Config.JWT;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.realestate.invest.Config.CustomUserDetailsService;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * The {@code JwtAuthenticationFilter} class is responsible for filtering and handling JWT-based authentication.
 * It extracts the JWT token from the request header, validates it, and sets the authentication context if valid.
 * 
 * @author Abhishek Srivastav
 * @version 1.0
 * @since 2023-09-01
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter 
{

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    
    @Autowired
    private JwtHelper jwtHelper;


    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Filters incoming HTTP requests to handle JWT-based authentication.
     *
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @param filterChain The filter chain for processing the request.
     * @throws ServletException If there is an issue with the servlet handling.
     * @throws IOException If there is an issue with I/O operations.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
    {

        String requestHeader = request.getHeader("x-auth-token");
        String username = null;
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")) 
        {
            
            token = requestHeader.substring(7);
            try 
            {
                username = this.jwtHelper.getUsernameFromToken(token);
            } 
            catch (IllegalArgumentException e) 
            {
                logger.info("Illegal Argument while fetching the username !!");
            } 
            catch (ExpiredJwtException e) 
            {
                logger.info("Given jwt token is expired !!");
            } 
            catch (MalformedJwtException e) 
            {
                logger.info("Some changed has done in token !! Invalid Token");
            } 
            catch (Exception e) 
            {
                ErrorResponse errorResponse = new ErrorResponse("Invalid Token Format, "+e.getMessage(), "400", HttpStatus.BAD_REQUEST);
                logger.info(errorResponse.toString());
            }
        } 

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) 
        {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken) 
            {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info(" X-Auth-Token :  {}", requestHeader);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } 
            else 
            {
                logger.info("Validation fails !!");
            }
        }

        filterChain.doFilter(request, response);
    }
    
}
