package com.realestate.invest.DTOModel;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @class represents a Data Transfer Object (DTO) for user information.
 * It contains various user-related fields such as phone number, password, first name,
 * last name, email, enabled status, roles, and OTP (One-Time Password).
 *
 * @Author Abhishek Srivastav
 * @since 2023-09-01
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class UserDTO 
{
    
    private Long id;

    // @JsonProperty("departmentId")
    // @NotNull(message = "Department cannot be null or empty")
    // private Long departmentId;

    // @JsonProperty("designationId")
    // @NotNull(message = "Designation cannot be null or empty")
    // private Long designationId;

    // @JsonProperty("organizationId")
    // private Long organizationId;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @NotNull(message = "Phone is your username so phone cannot be null or empty")
    @JsonProperty("phone")
    private String phone;

    @JsonProperty("password")
    private String password;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;
    
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)$", message = "Invalid email format")
    @JsonProperty("email")
    private String email;
    
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
    
    @JsonProperty("role")
    private Set<RoleDTO> roles;

    @JsonProperty("otp")
    private String otp;

}
