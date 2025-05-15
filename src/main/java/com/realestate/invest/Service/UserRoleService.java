package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.DTOModel.UserRoleDTO;
import com.realestate.invest.Model.UserRole;

/**
 * @Service interface for managing user roles.
 *
 * This interface defines methods for retrieving all user roles and deleting
 * user roles by their unique identifier.
 *
 * @Author: Abhishek Srivastav
 */
@Component
public interface UserRoleService 
{

    /**
     * @Retrieves a list of all user roles.
     *
     * This method returns a list containing all user roles along with their
     * associated details.
     *
     * @return List of all user roles.
     */
    List<UserRole> getAllUserWithRoles();
    
    /**
     * @Deletes a user role by its unique identifier.
     *
     * This method attempts to delete a user role based on the provided user role
     * identifier.
     *
     * @param userRole_id The unique identifier of the user role to be deleted.
     * @throws Exception If the user role is not found or an error occurs during
     *  deletion.
     */
    void deleteUserRoleById(Long userRole_id) throws Exception;

    /**
     * @Updates the user role for a specified user ID.
     *
     * @param uDto     The UserRoleDTO object containing the updated user role information.
     * @param user_id  The Long of the user whose role is to be updated.
     * @return         The updated UserRole object after the role has been successfully updated.
     * @throws Exception If an error occurs during the user role update process.
     */
    UserRole updateUserRoleByUserId(UserRoleDTO uDto, Long user_id) throws Exception;

    /**
     * @Retrieves the user role for a specified user ID.
     *
     * @param user_id  The Long of the user for whom the role information is to be retrieved.
     * @return         The UserRole object representing the user's role.
     * @throws Exception If an error occurs during the user role retrieval process.
     */
    UserRole getUserRoleByUserId(Long user_id) throws Exception;
    
    
}
