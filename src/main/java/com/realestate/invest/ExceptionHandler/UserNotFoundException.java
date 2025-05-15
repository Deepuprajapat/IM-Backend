package com.realestate.invest.ExceptionHandler;

/**
 * @UserNotFoundException is an exception class that is thrown when a user with a specific username
 * is not found in the database.
 *
 * @author Abhishek Srivastav
 * @since 04/09/2023
 */
public class UserNotFoundException extends Exception 
{
    /**
     * @Constructs a new UserNotFoundException with a default error message.
     * The default error message is "User with this username not found in database !!"
     */
    public UserNotFoundException() 
    {
        super("User with this username not found in database !!");
    }

    /**
     * @Constructs a new UserNotFoundException with a custom error message.
     *
     * @param msg The custom error message to be associated with this exception.
     */
    public UserNotFoundException(String msg) 
    {
        super(msg);
    }
    
}
