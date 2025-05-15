package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.DTOModel.UserDTO;
import com.realestate.invest.Model.User;

/**
 *  {@code UserService} interface defines a set of operations to manage user-related actions.
 * Users can be created, retrieved, updated, and managed using these methods.
 * @author Abhishek Srivastav
 * @version 1.0
 * @since 1/9/2023
 */
@Component
public interface UserService  
{
    /**
     * Saves a new user with roles in the system.
     *
     * @param userDTO The user data transfer object containing user information.
     * @return The saved user with roles.
     * @throws Exception If a user with the same attributes already exists.
     */
    User saveUserWithRoles(UserDTO userDTO) throws Exception;

    /**
     * Retrieves a list of all users in the system.
     *
     * @return A list of all users.
     */
    List<User> showAllUsers(Boolean isVerify, Boolean isEnabled, Boolean isEmailVerified);

    /**
     * Finds a user by their phone number.
     *
     * @param phone The phone number of the user to retrieve.
     * @return An optional containing the user if found, or empty if not found.
     */
    User findByPhone(String phone) throws Exception;

    /**
     * Finds a user by their unique identifier (Long).
     *
     * @param Long The unique identifier (Long) of the user to retrieve.
     * @return The user with the specified Long.
     */
    User findById(Long userId) throws Exception;

    /**
     * Deletes a User entity by its unique identifier.
     *
     * @param String The phone of the User to delete.
     * @throws Exception If there is an error while deleting the User.
     */
    void deleteUserById(Long user_id) throws Exception;

    /**
     * @Verifies or unverifies a user based on the provided ID.
     *
     * @param Id       The unique identifier of the user.
     * @param isVerify A boolean value indicating whether to verify the user (true) or unverify the user (false).
     * @return The User object after verification status update.
     * @throws Exception If an error occurs during the verification process.
     */
    User verifyUser(Long Id, Boolean isVerify) throws Exception;

    /**
     * @Disables or enables a user based on the provided ID.
     *
     * @param Id        The unique identifier of the user.
     * @param isEnabled A boolean value indicating whether to enable the user (true) or disable the user (false).
     * @return A message indicating the result of the operation.
     * @throws Exception If an error occurs during the operation.
    */
    String disableUser(Long Id, Boolean isEnabled) throws Exception;

    /**
     * Updates an existing user's information.
     *
     * @param userDTO The user data transfer object containing updated user information.
     * @param phone   The phone number of the user to update.
     * @return The updated user.
     * @throws Exception If a user with the same attributes already exists.
     */
    User updateuserById(Long Id, UserDTO userDTO) throws Exception;



}