package com.realestate.invest.Model;

import org.springframework.security.core.GrantedAuthority;

/**
 * This class represents a Authority entity in the application.
 *
 * @Author Abhishek Srivastav
 * @since 01/9/2023
 */
public class Authority implements GrantedAuthority
{

    private String authority;

    public Authority(String authority) 
    {
        this.authority = authority;
    }

    @Override
    public String getAuthority() 
    {
        return this.authority;
    }
    
}
