package com.realestate.invest.ServiceImpl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.realestate.invest.DTOModel.RoleDTO;
import com.realestate.invest.DTOModel.UserDTO;
import com.realestate.invest.Model.Role;
import com.realestate.invest.Model.User;
import com.realestate.invest.Model.UserRole;
import com.realestate.invest.Repository.RoleRepository;
import com.realestate.invest.Repository.UserRepository;
import com.realestate.invest.Service.UserService;

@Service
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public User saveUserWithRoles(UserDTO userDTO) throws Exception 
    {
        User local = this.userRepository.findByMobilePhone(userDTO.getPhone());
        if (local != null) 
        {
            throw new Exception("User is already there !!");
        } 
    
        // UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // User admin = userRepository.findByMobilePhone(userDetails.getUsername());
        // if(admin == null)
        // {
        //     throw new Exception("You are not authorized person to add new employee");
        // }
        // boolean isAdmin = admin.getUserRoles().stream().anyMatch(role -> role.getRole().getRoleName().equals("ADMIN"));
        // if(!isAdmin)
        // {
        //     throw new Exception("You are not authorized person to add new employee");
        // }
            
        User user = new User();
        user.setCreatedOn(new Date().getTime());
        user.setPhone(userDTO.getPhone());
        user.setEmail(userDTO.getEmail());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAlternateMobile(userDTO.getAlternateMobile());
        user.setPhoto(userDTO.getPhoto());
        user.setGender(userDTO.getGender());
        user.setCurrentAddress(userDTO.getCurrentAddress());
        user.setPermanentAddress(userDTO.getPermanentAddress());
        user.setDob(userDTO.getDob());
        
        // user.setCreatedById(admin.getId());
        
        Set<UserRole> userRoles = new HashSet<>();
        Set<RoleDTO> roles = userDTO.getRoles();
        if (roles != null) 
        {   
            for (RoleDTO roleDTO : roles) 
            {
                Role role = roleRepository.findByRoleName(roleDTO.getRoleName());
                if (role != null) 
                {
                    userRoles.add(new UserRole(user, role));
                } 
                else 
                {
                    throw new Exception("Role not found for roleName: " + roleDTO.getRoleName());
                }
            }
        }
        return this.userRepository.save(user);
    }
    
    @Override
    public User findByPhone(String phone) throws Exception
    {
        User user = this.userRepository.findByPhone(phone);
        if(user == null)
        {
            throw new Exception("User not found");
        }
        return user;
    }


    @Override
    public User findById(Long userId) throws Exception
    {
        User user = this.userRepository.findById(userId).orElse(null);
        if(user == null)
        {
            throw new Exception("User not found");
        }
        return user;
    }


    @Override
    public void deleteUserById(Long user_id) throws Exception
    {
        User user = this.userRepository.findById(user_id).orElse(null);
        if(user != null)
        {
            this.userRepository.deleteById(user_id);
        }
        else
        {
            throw new Exception("User not found");
        }
    }


    @Override
    public List<User> showAllUsers(Boolean isVerify, Boolean isEnabled, Boolean isEmailVerified) 
    {
        List<User> users = this.userRepository.findAll();
        return users.stream()
            .filter(user ->
            (isVerify == null || user.isVerified() == isVerify) &&
            (isEnabled == null || user.isEnabled() == isEnabled) &&
            (isEmailVerified == null || user.isEmailVerified() == isEmailVerified))
            .collect(Collectors.toList());
    }

    @Override
    public User verifyUser(Long Id, Boolean isVerify) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User admin = userRepository.findByMobilePhone(userDetails.getUsername());
        if(admin == null)
        {
            throw new Exception("You are not authorized person to add new employee");
        }
        boolean isAdmin = admin.getUserRoles().stream().anyMatch(role -> role.getRole().getRoleName().equals("ADMIN"));
        if(!isAdmin)
        {
            throw new Exception("You are not authorized person to add new employee");
        }
        User user = this.userRepository.findById(Id).orElse(null);
        if(user == null)
        {
            throw new Exception("User not found");
        }
        if(isVerify != null)
        {
            user.setVerified(isVerify);
            user.setEnabled(isVerify);
        }
        user.setUpdatedById(admin.getId());
        user.setUpdatedOn(new Date().getTime());
        return this.userRepository.save(user);
    }


    @Override
    public String disableUser(Long Id, Boolean isEnabled) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User admin = userRepository.findByMobilePhone(userDetails.getUsername());
        if(admin == null)
        {
            throw new Exception("You are not authorized person to add new employee");
        }
        boolean isAdmin = admin.getUserRoles().stream().anyMatch(role -> role.getRole().getRoleName().equals("ADMIN"));
        if(!isAdmin)
        {
            throw new Exception("You are not authorized person to add new employee");
        }
        User user = this.userRepository.findById(Id).orElse(null);
        if(user == null)
        {
            throw new Exception("User not found");
        }
        String message = "User has not disabled or enabled";
        if(isEnabled != null)
        {
            user.setEnabled(isEnabled);
            message = "User status changed successfully";
        }
        user.setUpdatedOn(new Date().getTime());
        this.userRepository.save(user);
        return message;
    }


    @Override
    public User updateuserById(Long Id, UserDTO userDTO) throws Exception 
    {
        User user =  this.userRepository.findById(Id).orElse(null);
        if(user == null)
        {
            throw new Exception("User not found");
        }
        if (userDTO.getFirstName() != null) 
        {
            user.setFirstName(userDTO.getFirstName());
        }

        if (userDTO.getLastName() != null) 
        {
            user.setLastName(userDTO.getLastName());
        }
        
        if (userDTO.getPhoto() != null) 
        {
            user.setPhoto(userDTO.getPhoto());
        }
        
        if (userDTO.getGender() != null) 
        {
            user.setGender(userDTO.getGender());
        }
        
        if (userDTO.getCurrentAddress() != null) 
        {
            user.setCurrentAddress(userDTO.getCurrentAddress());
        }
        
        if (userDTO.getPermanentAddress() != null) 
        {
            user.setPermanentAddress(userDTO.getPermanentAddress());
        }
        
        if (userDTO.getAlternateMobile() != null) 
        {
            user.setAlternateMobile(userDTO.getAlternateMobile());
        }
        
        if(userDTO.getEmail() != null && !user.getEmail().equals(userDTO.getEmail()))
        {
            User emailUser = this.userRepository.findByEmail(userDTO.getEmail());
            {
                if(emailUser != null && !emailUser.equals(user))
                {
                    throw new Exception("This email is belongs to another user");
                }
                if(userDTO.getOtp() == null)
                {
                    throw new Exception("Otp cannot be null or empty");
                }
                if(!userDTO.getOtp().equals(user.getOtp()))
                {
                    throw new Exception("Invalid OTP");
                }
                user.setEmail(userDTO.getEmail());
                user.setEmailVerified(true);
            }
        }
        if(userDTO.getPhone() != null && !userDTO.getPhone().equals(user.getPhone()))
        {
            if(userDTO.getPhone().length() == 10)
            {
                user.setPhone(userDTO.getPhone());
                user.setVerified(false);
                user.setEnabled(false);
            }
            else
            {
                throw new Exception("Phone length is not equals to 10 digits "+ userDTO.getPhone().length());
            }
        }
        return this.userRepository.save(user);
    }


    
}
