package com.realestate.invest.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.realestate.invest.Model.Developer;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Model.Property;
import com.realestate.invest.Model.User;

public interface PropertyRepository extends JpaRepository <Property, Long>, JpaSpecificationExecutor<Property>
{
    
    List<Property> findByUser(User user);
    
    List<Property> findByIdIn(List<Long> Ids);

    List<Property> findByDeveloper(Developer developer);

    Property findByPropertyUrl(String propertyUrl);

    List<Property> findByProject(Project project);

}
