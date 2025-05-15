package com.realestate.invest.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.DTOModel.UserRoleDTO;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.UserRole;
import com.realestate.invest.Service.UserRoleService;
/**
 * @Controller class for managing user roles.
 *
 * This controller provides endpoints for retrieving all user roles and
 * deleting user roles by their unique identifier.
 *
 * @Author: Abhishek Srivastav
 */
@CrossOrigin("*")
@RestController
public class UserRoleController 
{

    @Autowired
    private UserRoleService uRoleService;

    /**
     * @Retrieves all user roles.
     *
     * This endpoint returns a list of all user roles along with their associated
     * details. It responds with a JSON array containing user role information.
     *
     * @return ResponseEntity containing a list of user roles and HTTP status OK.
     */
    @GetMapping("/user/get/all/userrole")
    public ResponseEntity<?> getAllUserRoles()
    {
        List<UserRole> userRoles = this.uRoleService.getAllUserWithRoles();
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

    /**
     * @Deletes a user role by its unique identifier.
     *
     * This endpoint attempts to delete a user role based on the provided user role
     * identifier. If the user role is deleted successfully, a success response is
     * returned. If the user role is not found or an error occurs, an error response
     * with a 404 status code is returned.
     *
     * @param userRole_id The unique identifier of the user role to be deleted.
     * @return ResponseEntity containing a success response if the user role is
     *         deleted successfully, or an error response if the user role is not
     *         found.
     */
    @DeleteMapping("/user/delete/user/role/by/id/{userRole_id}")
    public ResponseEntity<?> deleteUserRoleById(@PathVariable Long userRole_id)
    {
        try
        {
            this.uRoleService.deleteUserRoleById(userRole_id);
            SuccessResponse successResponse = new SuccessResponse("User role has been deleted successfully");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "404", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @Updates the user role for a specified user ID using a PATCH request.
     *
     * @param uDto     The UserRoleDTO object containing the updated user role information.
     * @param user_id  The Long of the user whose role is to be updated.
     * @return         ResponseEntity containing the updated UserRole object if successful, or an ErrorResponse if an error occurs.
     */
    @PatchMapping("/user/update/role/by/user/id/{user_id}")
    public ResponseEntity<?> updateUserRoleByUserId(@RequestBody UserRoleDTO uDto, @PathVariable Long user_id)
    {
        try
        {
            UserRole userRole = this.uRoleService.updateUserRoleByUserId(uDto, user_id);
            return new ResponseEntity<>(userRole, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "404", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @Retrieves the user role for a specified user ID using a GET request.
     *
     * @param user_id  The Long of the user for whom the role information is to be retrieved.
     * @return  ResponseEntity containing the UserRole object representing the user's role if successful, or an ErrorResponse if an error occurs.
     */
    @GetMapping("/user/get/role/by/user/id/{user_id}")
    public ResponseEntity<?> getUserRoleByUserId(@PathVariable Long user_id)
    {
        try
        {
            UserRole userRole = this.uRoleService.getUserRoleByUserId(user_id);
            return new ResponseEntity<>(userRole, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "404", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    
}
