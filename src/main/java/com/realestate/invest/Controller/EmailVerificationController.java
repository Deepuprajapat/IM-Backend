package com.realestate.invest.Controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.Model.User;
import com.realestate.invest.Repository.UserRepository;
import com.realestate.invest.Service.EmailVerificationService;
import com.realestate.invest.Utils.OTPGenerator;

@CrossOrigin("*")
@RestController
@RequestMapping("/email")
public class EmailVerificationController
{

    Logger logger = LoggerFactory.getLogger(EmailVerificationController.class);

@Autowired
private EmailVerificationService emailService;

@Autowired
private UserRepository userRepository;

    private Map<String, String> emailToOTPMap = new HashMap<>();

    /**
     * @Send an OTP to the user's email address for password reset.
     *
     * @param email The email address of the user.
     * @return A ResponseEntity indicating the result of sending the OTP.
     */
    @PostMapping("/send-otp/{userId}")
    public ResponseEntity<?> sendOTP(@PathVariable Long userId,@RequestParam String email) 
    {
        try
        {
            User user = userRepository.findById(userId).orElse(null);
            Map<String, String> response = new HashMap<>();
            if (user == null) 
            {
                response.put("Message", "User not found.");
                logger.info("Message, User not found.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            if(user.getEmail()!= null && user.getEmail().equals(email) && user.isEmailVerified())
            {
                response.put("Message", "Email is already added and verified.");
                logger.info("Message, Email is already added and verified.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            String otp = OTPGenerator.generateOTP(6);
            System.out.println("OTP : "+otp);
            emailToOTPMap.put(email, otp);

            String subject = "Email Verification OTP From LMS";
            String message = "<html>" +
                            "<head>" +
                            "<style>" +
                            "    body {font-family: Arial, sans-serif;}" +
                            "    .container {border: 1px solid #e2e2e2; padding: 20px;}" +
                            "    .otp {font-size: 24px;}" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            "<div class='container'>" +
                            "    <h1>Your Email Verification OTP</h1>" +
                            "    <h2>Dear "+user.getFirstName() +" "+user.getLastName()+",</h2>" +
                            "    <p>Your Email verification OTP is: <span class='otp'>" + otp + "</span></p>" +
                            "    <p>Please use this OTP to verify your email address on LMS.</p>" +
                            "    <p>Thank you!</p>" +
                            "</div>" +
                            "</body>" +
                            "</html>";

            boolean flag = emailService.sendEmail(subject, message, email);
            if (flag) 
            {
                user.setOtp(otp);
                response.put("message", "OTP sent successfully. Please check your email.");
                userRepository.save(user);
                System.out.println("''''''''''''''''''"+user.getOtp());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } 
            else 
            {
                response.put("message", "Failed to send OTP. Please check your email.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        }
        catch(Exception e)
        {
            String messsage = " Failed to send OTP : ";
            ErrorResponse errorResponse = new ErrorResponse(messsage+e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            logger.info(e.getLocalizedMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Verify the OTP provided by the user.
     *
     * @param otp   The OTP provided by the user.
     * @param email The email address of the user.
     * @param model The model for rendering the view.
     * @return A ResponseEntity indicating the result of OTP verification.
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String otp, @RequestParam String email) 
    {
        Map<String, String> response = new HashMap<>();

        System.out.println("Email : "+email+" "+"OTP : "+otp);
        if (emailToOTPMap.containsKey(email) && emailToOTPMap.get(email).equals(otp)) 
        {
            User user = this.userRepository.findByEmail(email);
            if (user == null) 
            {
                response.put("message", "You are not an authorized user.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
            if (user.getOtp().equals(otp)) 
            {
                user.setEmailVerified(true);
                userRepository.save(user);
                response.put("message", "OTP verified successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } 
            else 
            {
                response.put("message", "Invalid OTP.");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } 
        else 
        {
            response.put("message", "Invalid OTP or email.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
