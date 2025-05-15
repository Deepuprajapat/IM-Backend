package com.realestate.invest.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.Model.Role;
import com.realestate.invest.Service.RoleService;

/**
 * {@code RoleController} handles HTTP requests related to roles in the application.
 * It allows the creation of new roles through a POST request.
 * 
 * @author Abhishek Srivastav
 * 
 */

@CrossOrigin("*")
@RestController
public class RoleController 
{
    
    @Autowired
    private RoleService roleService;

    /**
     * @Handles the creation of a new role through a POST request.
     * 
     * @param role The Role object containing information about the new role.
     * @return ResponseEntity containing the created role or an error response.
     * @see RoleService#saveNewRole(Role)
     */
    @PostMapping("/user/save/new/role")
    public ResponseEntity<?> saveNewRoles(@RequestBody Role role)
    {
        try
        {
            Role roles = this.roleService.saveNewRole(role);
            return new ResponseEntity<>(roles, HttpStatus.CREATED); 
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * @Retrieves a role by its ID using a GET request.
     *
     * @param id The ID of the role to be retrieved.
     * @return ResponseEntity containing the Role object if successful, or an ErrorResponse if an error occurs.
     */
    @GetMapping("/user/get/role/by/id/{id}")
    public ResponseEntity<?> getRoleById(@PathVariable Long id)
    {
        try
        {
            Role roles = this.roleService.getRoleById(id);
            return new ResponseEntity<>(roles, HttpStatus.CREATED); 
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Retrieves all roles using a GET request.
     *
     * @return ResponseEntity containing a list of Role objects if successful, or an ErrorResponse if an error occurs.
     */
    @GetMapping("/user/get/all/role")
    public ResponseEntity<?> getAllRoles() 
    {
        try
        {
            List<Role> roles = this.roleService.getAllRoles();
            return new ResponseEntity<>(roles, HttpStatus.CREATED); 
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
