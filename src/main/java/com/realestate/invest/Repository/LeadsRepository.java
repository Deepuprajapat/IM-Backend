package com.realestate.invest.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.realestate.invest.Model.Leads;
import com.realestate.invest.Model.Property;
import com.realestate.invest.Model.User;

import jakarta.transaction.Transactional;

public interface LeadsRepository extends JpaRepository<Leads, Long>
{

    Leads findByPhone(String phone);

    List<Leads> findByProjectName(String projectname);

    List<Leads> findByEmail(String email);

    Leads findByPhoneAndOtp(String phone, String otp); 

    @Transactional
    @Query(value = "SELECT l.* FROM leads l INNER JOIN project p ON l.project_name = p.project_name WHERE l.project_name IN (:projectNames)", nativeQuery = true)
    List<Leads> findByProjectLeads(@Param("projectNames") List<String> projectNames);

    List<Leads> findByPropertyIn(List<Property> properties);
    
    List<Leads> findByPropertyUserAndPropertyIsNotNull(User user);


}
