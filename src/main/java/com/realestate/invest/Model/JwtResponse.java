package com.realestate.invest.Model;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents a Response entity in the application.
 *
 * @Author Abhishek Srivastav
 * @since 01/9/2023
 */
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@ToString
@Builder
public class JwtResponse 
{
    public String token;
    public String username;
    public Long userId;
    public Collection<? extends GrantedAuthority> role;
    public String name;
}