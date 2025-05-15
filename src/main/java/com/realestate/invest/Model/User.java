package com.realestate.invest.Model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @This class represents a User entity in the application.
 *
 * @Author Abhishek Srivastav
 * @Date: 2023-09-01
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("phone")
    @Column(unique = true, nullable = false)
    private String phone;

    @JsonIgnore
    private String password;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("enabled")
    private boolean enabled = true;
    
    @JsonProperty("createdOn")
    private Long createdOn;
    
    @JsonProperty("updatedOn")
    private Long updatedOn;
    
    @JsonProperty("photo")
    private String photo;

    @JsonProperty("alternateMobile")
    private String alternateMobile;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("currentAddress")
    private String currentAddress;

    @JsonProperty("permanentAddress")
    private String permanentAddress;

    @JsonProperty("dob")
    private String dob;

    @JsonProperty("lastLoginTIme")
    private Long lastLoginTIme;

    @JsonProperty("parentId")
    private Long parentId;

    @JsonProperty("createdById")
    private Long createdById;

    @JsonProperty("updatedById")
    private Long updatedById;

    @JsonProperty("isVerified")
    @Column(columnDefinition = "BIT default false")
    private boolean isVerified;

    @JsonProperty("isEmailVerified")
    private boolean isEmailVerified; 

    @JsonProperty("isActive")
    @Column(columnDefinition = "BIT default false")
    private boolean isActive;
    
    @JsonIgnore
    private String otp;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() 
    {
        Set<Authority> set = new HashSet<>();
        this.userRoles.forEach(userRole -> 
        {
            set.add(new Authority(userRole.getRole().getRoleName()));
        });

        return set;
    }


    @Override
    public boolean isAccountNonExpired() 
    {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() 
    {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() 
    {
        return true;
    }

    @JsonIgnore
    @Override
    public String getUsername() 
    {
        return phone;
    }

    
}
