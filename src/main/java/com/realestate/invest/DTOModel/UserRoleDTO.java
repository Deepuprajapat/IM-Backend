package com.realestate.invest.DTOModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @class represents a Data Transfer Object (DTO) for User Role DTO.
 *
 * @Author Abhishek Srivastav
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRoleDTO 
{
    @JsonProperty("name")
    private String roleName;

    @JsonProperty("userId")
    private Long userId;
    
}
