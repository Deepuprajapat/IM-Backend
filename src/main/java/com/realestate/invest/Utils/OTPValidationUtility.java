package com.realestate.invest.Utils;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class OTPValidationUtility 
{

    private static final String BASE_URL = "http://servermsg.com/api/SmsApi/SendSingleApi";

    public static void sendSms(String phoneNumber, String otp) throws Exception 
    {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        if (otp == null || !otp.matches("\\d{4,6}")) 
        {
            throw new IllegalArgumentException("Invalid OTP");
        }

        String message = otp + " is your OTP. Please enter the OTP to verify your mobile number. For more info visit investmango.com";
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);

        String completeUrl = String.format(
            "%s?UserID=%s&Password=%s&SenderID=%s&Phno=%s&Msg=%s&EntityID=%s&TemplateID=%s",
            BASE_URL,
            SecretUtils.USERID,
            URLEncoder.encode(SecretUtils.PASSWORD, StandardCharsets.UTF_8),
            SecretUtils.SENDERID,
            phoneNumber,
            encodedMessage,
            SecretUtils.ENTITYID,
            SecretUtils.TEMPLATEID
        );

        RestTemplate restTemplate = new RestTemplate();

        try 
        {
            ResponseEntity<String> response = restTemplate.getForEntity(completeUrl, String.class);
            if (response.getStatusCode().is2xxSuccessful()) 
            {
                System.out.println("SMS sent successfully. Response: " + response.getBody());
            } 
            else 
            {
                throw new Exception("Failed to send SMS. HTTP status: " + response.getStatusCode());
            }
        } 
        catch (Exception e) 
        {
            throw new Exception("Error while sending SMS: " + e.getMessage(), e);
        }
    }

    
}