package com.realestate.invest.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents a JwtRequest entity in the application.
 *
 * @Author Abhishek Srivastav
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class JwtRequest 
{
    String userName;
    String password;
    String otp;
}