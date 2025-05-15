package com.realestate.invest.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @This class represents a UserRole entity in the application.
 *
 * @Author Abhishek Srivastav
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class UserRole 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JsonIgnore
    @ManyToOne
    private Role role;

    public UserRole(User user, Role role) 
    {
        this.user = user;
        this.role = role;
        user.getUserRoles().add(this);
    }
    
    private transient Long userId;

    @JsonProperty("userName")
    private transient String name;
    
    private transient String userRole;

}
