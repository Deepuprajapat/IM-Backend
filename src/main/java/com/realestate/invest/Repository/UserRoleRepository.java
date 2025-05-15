package com.realestate.invest.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.realestate.invest.Model.UserRole;
import jakarta.transaction.Transactional;

/**
 * @Repository interface for managing UserRoles in the database.
 *
 * This interface extends the JpaRepository, providing CRUD operations for
 * @UserRoles with their unique identifier of type Long.
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long>
{
    
    @Transactional
    @Query(value = "select * from user_role where id = ?1", nativeQuery = true)
    public UserRole getByUserRoleId(Long id);

    @Transactional
    @Modifying
    @Query(value = "delete from user_role where id = ?1", nativeQuery = true)
    public UserRole deleteByUserRoleId(Long id);

    @Transactional
    @Query(value = "select * from user_role", nativeQuery = true)
    public List<UserRole> getAllUserRoles();

    @Transactional
    @Query(value = "select * from user_role where user_id = ?1", nativeQuery = true)
    public UserRole getUserRoleByUserId(Long user_id);


}
