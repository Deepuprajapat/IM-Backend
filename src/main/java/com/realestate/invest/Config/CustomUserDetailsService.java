package com.realestate.invest.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.realestate.invest.Model.User;
import com.realestate.invest.Repository.UserRepository;

/**
 * The {@code CustomUserDetailsService} class configures security settings for the application.
 * It sets up user authentication, authorization rules, CORS configuration, and JWT-based authentication.
 * 
 * @author Abhishek Srivastav
 * @version 1.0
 * @since 2023-09-01
 */
@Component
public class CustomUserDetailsService implements UserDetailsService 
{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        User user = this.userRepository.findByPhone(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("User Not Found");
        }
        if(!user.isEnabled())
        {
            throw new UsernameNotFoundException("No user found !!");
        }
        return user;
    }
    
}

