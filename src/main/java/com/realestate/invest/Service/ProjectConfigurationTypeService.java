package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.Model.ProjectConfigurationType;

@Component
public interface ProjectConfigurationTypeService 
{
    ProjectConfigurationType saveNewPropertyConfigurationType(ProjectConfigurationType pctDto) throws Exception;

    ProjectConfigurationType findById(Long Id) throws Exception;

    ProjectConfigurationType updatePropertyConfigurationTypeById(Long Id, ProjectConfigurationType pctDto) throws Exception;

    String deletePropertyConfigurationTypeById(Long Id) throws Exception;

    List<ProjectConfigurationType> getAllPropertyConfigurationType(PropertyType propertyType);
    
}
