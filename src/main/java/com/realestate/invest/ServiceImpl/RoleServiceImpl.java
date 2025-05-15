package com.realestate.invest.ServiceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realestate.invest.Model.Role;
import com.realestate.invest.Repository.RoleRepository;
import com.realestate.invest.Service.RoleService;

@Service
public class RoleServiceImpl implements RoleService 
{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role saveNewRole(Role role) throws Exception
    {
        Role role2 = this.roleRepository.findByRoleName(role.getRoleName());
        if(role2 == null)
        {
            throw new Exception("Role is already exist");
        }
        Role roles = new Role();
        roles.setRoleName(role.getRoleName());
        return this.roleRepository.save(roles);
    }

    @Override
    public Role getRoleById(Long role_id) throws Exception
    {
        Role role = this.roleRepository.findById(role_id)
        .orElseThrow(() -> new Exception("Role not found"));
        
        return role;
    }

    @Override
    public List<Role> getAllRoles() 
    {
        return this.roleRepository.findAll();
    }
    
}
