package com.realestate.invest.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.realestate.invest.Model.Role;
import jakarta.transaction.Transactional;

/**
 *  The {@code RoleRepository} interface provides data access methods for the Role entity.
 *  It extends the JpaRepository interface for basic CRUD operations and defines custom queries for retrieving Roles by ID.
 * @Author: Abhishek Srivastav
 * @version 1.0
 * @Date: 2023-09-01
 */
public interface RoleRepository extends JpaRepository <Role, Long> 
{
    Role findByroleName(String roleName);

    Role findByRoleName(String roleName);

    @Transactional
    @Query(value = "select * from role where id = ?1",nativeQuery = true)
    public Role findRoleByRoleId(Long role_id);

}
