package com.realestate.invest.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author Abhishek Srivastav
 * @version 1.0
 * @since 19/09/2023
 * @implNote it class is used for custom exception & response handling 
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ControllerAdvice
@JsonIgnoreProperties({"cause", "stackTrace","suppressed","localizedMessage"})
public class ErrorResponse extends Exception
{   
    public ErrorResponse(String message) 
    {
        this.message = message;
    }
    private String message;
    private String responseCode;
    private HttpStatus httpStatus;
    
}
