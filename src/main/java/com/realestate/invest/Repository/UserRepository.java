package com.realestate.invest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.realestate.invest.Model.User;
import jakarta.transaction.Transactional;


/**
 *  The {@code UserRepository} interface provides data access methods for the User entity.
 *  It extends the JpaRepository interface for basic CRUD operations and defines custom queries for retrieving User by ID.
 * @Author: Abhishek Srivastav
 * @version 1.0
 * @Date: 2023-09-01
 */
public interface UserRepository extends JpaRepository <User, Long>
{
    User findByPhone(String phone);

    // @Transactional
    // @Query(value = "select * from user WHERE phone = ?1",nativeQuery = true)
    // public User findByMobilePhone(String phone);
    @Transactional
    @Query(value = "SELECT * FROM user WHERE phone = ?1", nativeQuery = true)
    public User findByMobilePhone(String phone);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `user` WHERE phone = ?1", nativeQuery = true)
    public void deleteByphone(String phone);

    public User findByEmail(String email);

    public User findByEmailAndPhone(String email, String phone);

    public User findByIdAndEmail(Long id, String email);

}
