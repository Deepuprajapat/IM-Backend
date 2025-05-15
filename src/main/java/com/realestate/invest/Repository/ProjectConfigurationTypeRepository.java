package com.realestate.invest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.Model.ProjectConfigurationType;

public interface ProjectConfigurationTypeRepository extends JpaRepository<ProjectConfigurationType, Long>
{

    ProjectConfigurationType findByConfigurationTypeNameAndPropertyType(String typeName, PropertyType propertyType);
    
}
