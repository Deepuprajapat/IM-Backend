package com.realestate.invest.Utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * The {@code OTPGenerator} class provides a utility for generating One-Time Passwords (OTP).
 * It generates random numeric OTPs of the specified length.
 * 
 * @author Abhishek Srivastav
 */
public class OTPGenerator 
{
    /**
     * Generates a random numeric OTP of the specified length.
     *
     * @param length The length of the OTP to generate.
     * @return A random numeric OTP as a string.
     */
    public static String generateOTP(int length) 
    {
        StringBuilder otp = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) 
        {
            otp.append(random.nextInt(10)); // Generates a random digit (0-9)
        }
        return otp.toString();
    }

    static final SecureRandom random = new SecureRandom();

    public static String generateOTP() 
    {
        int otp = 100000 + random.nextInt(900000); 
        return String.valueOf(otp);
    }

    public static String generateUUID() 
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    
}