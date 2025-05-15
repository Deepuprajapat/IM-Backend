package com.realestate.invest.ExceptionHandler;

/**
 * The {@code UserFoundException} is an exception class that is thrown when a user with a specific username
 * already exists in the database.
 *
 * @author Abhishek Srivastav
 * @since 04/09/2023
 */
public class UserFoundException extends  Exception
{
    /**
     * @Constructs a new {@code UserFoundException} with a default error message.
     * The default error message is "User with this Username is already there in DB !! try with another one."
     */
    public UserFoundException() 
    {
        super("User with this Username is already there in DB !! try with another one");
    }

    /**
     * @Constructs a new {@code UserFoundException} with a custom error message.
     *
     * @param msg The custom error message to be associated with this exception.
     */
    public UserFoundException(String msg)
    {
        super(msg);
    }
}
