package com.realestate.invest.ServiceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realestate.invest.DTOModel.UserRoleDTO;
import com.realestate.invest.Model.Role;
import com.realestate.invest.Model.User;
import com.realestate.invest.Model.UserRole;
import com.realestate.invest.Repository.RoleRepository;
import com.realestate.invest.Repository.UserRepository;
import com.realestate.invest.Repository.UserRoleRepository;
import com.realestate.invest.Service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService
{

    @Autowired
    private UserRoleRepository uRoleRepository;

    @Autowired 
    private RoleRepository roleRepository;

    @Autowired 
    private UserRepository userRepository;


    @Override
    public List<UserRole> getAllUserWithRoles() 
    {
        return this.uRoleRepository.getAllUserRoles();
    }

    @Override
    public void deleteUserRoleById(Long userRole_id) throws Exception
    {
        UserRole userRole = this.uRoleRepository.getByUserRoleId(userRole_id);
        if(userRole != null) 
        {
            this.uRoleRepository.deleteById(userRole_id);
        }
        else
        {
            throw new Exception("User has not any role");
        }
    }

    @Override
    public UserRole updateUserRoleByUserId(UserRoleDTO uDto, Long user_id) throws Exception
    {
        User user = this.userRepository.findById(user_id)
        .orElseThrow(() -> new Exception("User not found"));

        boolean isAdmin = user.getUserRoles().stream().anyMatch(role -> role.getRole().getRoleName().equals("GP_ADMIN"));
        if(!isAdmin)
        {
            throw new Exception("You are not an authorize user");
        }

        UserRole existUserRole = this.uRoleRepository.getUserRoleByUserId(uDto.getUserId());

        this.userRepository.findById(uDto.getUserId())
        .orElseThrow(() -> new Exception("Selected user not found"));

        if(existUserRole != null)
        {
            Role role = this.roleRepository.findByRoleName(uDto.getRoleName());
            if(role != null)
            {
                if(existUserRole.getRole().equals(role))
                {
                    throw new Exception("Already same role has assigned");
                }
                else
                {
                    existUserRole.setRole(role);
                    existUserRole.setUserId(existUserRole.getUser().getId());
                    existUserRole.setName(existUserRole.getUser().getFirstName()+ " "+existUserRole.getUser().getLastName());
                    existUserRole.setUserRole(existUserRole.getRole().getRoleName());
                    return this.uRoleRepository.save(existUserRole);
                }
            }
            else
            {
                throw new Exception("Selected role not found");
            }
        }
        else
        {
            throw new Exception("User role not exist for this user");
        }   
    }

    @Override
    public UserRole getUserRoleByUserId(Long user_id) throws Exception
    {
        UserRole userRole = this.uRoleRepository.getUserRoleByUserId(user_id);
        if(userRole != null)
        {
            userRole.setUserId(userRole.getUser().getId());
            userRole.setName(userRole.getUser().getFirstName()+ " "+userRole.getUser().getLastName());
            userRole.setUserRole(userRole.getRole().getRoleName());
            return userRole;
        }
        else
        {
            throw new Exception("No data found for this user");
        }
    }
    
}
