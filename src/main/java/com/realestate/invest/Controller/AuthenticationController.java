package com.realestate.invest.Controller;


import java.security.Principal;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.Config.CustomUserDetailsService;
import com.realestate.invest.Config.JWT.JwtHelper;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.Model.JwtRequest;
import com.realestate.invest.Model.JwtResponse;
import com.realestate.invest.Model.RefreshToken;
import com.realestate.invest.Model.User;
import com.realestate.invest.Repository.UserRepository;

/**
 * @The AuthenticationController class handles user authentication and token generation
 * for the GoPropify application.
 *
 * @This controller provides endpoints for user login, token generation, and fetching
 * the currently authenticated user.
 *
 * @author Abhishek Srivastav.
 * @version 1.0
 * @since 01/09/2023
 */
@CrossOrigin("*")
@RequestMapping("/auth")
@RestController
public class AuthenticationController 
{
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    /**
     * Endpoint to generate a JWT token for the user based on their credentials.
     *
     * @param request The JwtRequest object containing user credentials (username and password).
     * @return ResponseEntity containing a JwtResponse with the generated JWT token.
     */
    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) 
    {
        this.doAuthenticate(request.getUserName(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        String token = this.helper.generateToken(userDetails);
        User user = (User) userDetails;
        user.setLastLoginTIme(new Date().getTime());
        user.setActive(true);
        userRepository.save(user);
        JwtResponse response = JwtResponse.builder()
                .token("Bearer "+token)
                .username(userDetails.getUsername())
                .userId(user.getId())
                .name(user.getFirstName() + " " + user.getLastName())
                .role(user.getAuthorities()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Helper method to authenticate a user with their username and password.
     *
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @throws BadCredentialsException If the provided credentials are invalid.
     */
    private void doAuthenticate(String username, String password) 
    {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        try 
        {
            manager.authenticate(authentication);
        } 
        catch (BadCredentialsException e) 
        {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    /**
     * Exception handler method for unauthorized credentials.
     *
     * @return A message indicating unauthorized credentials.
     */
    public String exceptionHandler() 
    {
        logger.info("Unauthorized Credentials");
        return "Unauthorized Credentials !!";
    }

    /**
     * Endpoint to fetch the currently authenticated user.
     *
     * @param principal The Principal object representing the currently authenticated user.
     * @return The User object representing the authenticated user.
     */
    @GetMapping("/current")
    public User getCurrentUser(Principal principal) 
    {
        return ((User) this.userDetailsService.loadUserByUsername(principal.getName()));
    }

    @RequestMapping(value = "/refresh-token", method = RequestMethod.POST)
    public ResponseEntity<?> refreshToken(@RequestBody RefreshToken request) 
    {
        try
        {
            String reqToken = request.getToken();
            String cleanToken = reqToken.substring(7);
            UserDetails userDetails = userDetailsService.loadUserByUsername(helper.extractUsername(cleanToken));
            String token = helper.generateRefreshToken(cleanToken);
            User user = (User) userDetails;
            user.setLastLoginTIme(new Date().getTime());
            user.setActive(true);
            userRepository.save(user);
            JwtResponse response = JwtResponse.builder()
                    .token("Bearer " + token)
                    .username(userDetails.getUsername())
                    .userId(user.getId())
                    .name(user.getFirstName() + " " + user.getLastName())
                    .role(user.getAuthorities()).build();
            logger.info("Refresh Token : "+"Bearer "+token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "500", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
