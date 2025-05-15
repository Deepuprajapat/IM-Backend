package com.realestate.invest.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.Service.UserService;
import com.realestate.invest.DTOModel.UserDTO;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.User;

/**
 * @author Abhishek Srivastav
 * @version 1.0
 * 
 * @Controller for handling user-related operations.
 */
@RestController
@RequestMapping("/user")
public class UserController 
{

    @Autowired
    private UserService userService;
    
    @Autowired 
    private Validator validator;

    /**
     * @Create a new user with specified roles.
     *
     * @param userDTO The user data to create.
     * @return ResponseEntity containing the created user or an HTTP conflict status if user already exists.
     * @throws Exception If the user already exists.
     */
    @PostMapping("/create/new")
    public ResponseEntity<?> createUserWithRoles(@RequestBody UserDTO userDTO, BindingResult bindingResult) throws Exception
    {
        validator.validate(userDTO, bindingResult);
        if (bindingResult.hasErrors()) 
        {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) 
            {
                errorMessage.append(fieldError.getDefaultMessage()).append(" ");
            }
            ErrorResponse errorResponse = new ErrorResponse(errorMessage.toString().trim(), "400", HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        else 
        {
            try
            {
                User user = this.userService.saveUserWithRoles(userDTO);
                return new ResponseEntity<>(user,HttpStatus.CREATED);
            }
            catch(Exception ex)
            {
                ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "400", HttpStatus.BAD_REQUEST);
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        }
    }
    
    /**
     * @Retrieve a list of all users.
     *
     * @return ResponseEntity containing the list of users or an HTTP status.
     */
    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public ResponseEntity<?> showAllUsers(@RequestParam(required = false) Boolean isVerify, 
    @RequestParam(required = false) Boolean isEnabled, @RequestParam(required = false) Boolean isEmailVerified)
    {
        try
        {
            List<User> users = this.userService.showAllUsers(isVerify, isEnabled, isEmailVerified);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Find a user by phone number.
     *
     * @param phone The phone number to search for.
     * @return ResponseEntity containing the user if found or an HTTP status.
     */
    @GetMapping("/find/by/phone/{phone}")
    public ResponseEntity<?> findByphone(@PathVariable String phone)
    {
        try
        {
            User users = this.userService.findByPhone(phone);
            return new ResponseEntity<>(users, HttpStatus.OK);  
        }
        catch(Exception ex)
        {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Find a user by Long (Unique Identifier).
     *
     * @param Long The Long of the user to find.
     * @return ResponseEntity containing the user if found or an HTTP status.
     */
    @GetMapping("/get/by/id/{userId}")
    public ResponseEntity<?> findById(@PathVariable Long userId)
    {
        try
        {
            User user =  this.userService.findById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * @Deletes a user based on the provided phone number.
     *
     * This endpoint is designed to delete a user by their phone number. If the user
     * with the specified phone number is found, it will be deleted, and a success
     * response will be returned. If the user is not found, an error response with
     * a 404 status code will be returned.
     *
     * @param phone The phone number of the user to be deleted.
     * @return ResponseEntity containing a success response if the user is deleted
     *         successfully, or an error response if the user is not found.
     */
    @DeleteMapping("/delete/by/id/{user_id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long user_id)
    {
        try
        {
            this.userService.deleteUserById(user_id);
            SuccessResponse successResponse = new SuccessResponse("User deleted successfully");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "404", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }


    @PatchMapping("/verify/by/id/{user_id}")
    public ResponseEntity<?> verifyUser(@PathVariable Long user_id, @RequestParam Boolean isVerified)
    {
        try
        {
            User user = this.userService.verifyUser(user_id, isVerified);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "404", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
    
    @PatchMapping("/disable/by/id/{user_id}")
    public ResponseEntity<?> disableUser(@PathVariable Long user_id, @RequestParam Boolean isDisabled)
    {
        try
        {
            String message = this.userService.disableUser(user_id, isDisabled);
            SuccessResponse successResponse = new SuccessResponse(message);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "404", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
    
    
    @PatchMapping("/update/by/id/{user_id}")
    public ResponseEntity<?> updateuserById(@PathVariable Long user_id, @RequestBody UserDTO userDTO)
    {
        try
        {
            User user = this.userService.updateuserById(user_id, userDTO);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "404", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

}
