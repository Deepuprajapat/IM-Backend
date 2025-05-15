package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.Model.Role;

/**
 * @Service interface for managing roles.
 *
 * This interface defines methods for saving new roles, retrieving roles by
 * their unique identifier, and retrieving a list of all roles.
 *
 * @Author: Abhishek Srivastav
 */
@Component
public interface RoleService 
{

    /**
     * @Saves a new role.
     *
     * This method is responsible for persisting a new role in the system.
     *
     * @param role The role to be saved.
     * @return The saved role.
     * @throws Exception If an error occurs during the saving process.
     */
    Role saveNewRole(Role role) throws Exception;

    /**
     * @Retrieves a role by its unique identifier.
     *
     * This method retrieves a role based on the provided role identifier.
     *
     * @param role_id The unique identifier of the role to be retrieved.
     * @return The retrieved role.
     * @throws Exception If the role is not found or an error occurs.
     */
    Role getRoleById(Long role_id) throws Exception;

    /**
     * @Retrieves a list of all roles.
     *
     * This method returns a list containing all roles available in the system.
     *
     * @return List of all roles.
     */
    List<Role> getAllRoles();
    
}
