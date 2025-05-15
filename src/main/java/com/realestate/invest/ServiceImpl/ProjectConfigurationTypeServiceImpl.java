package com.realestate.invest.ServiceImpl;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.Model.ProjectConfigurationType;
import com.realestate.invest.Repository.ProjectConfigurationTypeRepository;
import com.realestate.invest.Service.ProjectConfigurationTypeService;

@Service
public class ProjectConfigurationTypeServiceImpl implements ProjectConfigurationTypeService 
{

    @Autowired
    private ProjectConfigurationTypeRepository pctRepositry;

    @Override
    public ProjectConfigurationType saveNewPropertyConfigurationType(ProjectConfigurationType pctDto)
    throws Exception 
    {
        ProjectConfigurationType existing = this.pctRepositry.findByConfigurationTypeNameAndPropertyType(pctDto.getConfigurationTypeName(), 
        pctDto.getPropertyType());
        if(existing != null) throw new Exception(pctDto.getConfigurationTypeName()+" is already exist for "+pctDto.getPropertyType());
        ProjectConfigurationType pConfigurationType = new ProjectConfigurationType();
        pConfigurationType.setCreatedDate(new Date().getTime());
        pConfigurationType.setConfigurationTypeName(pctDto.getConfigurationTypeName().toUpperCase());
        pConfigurationType.setPropertyType(pctDto.getPropertyType());
        return this.pctRepositry.save(pConfigurationType);
    }

    @Override
    public ProjectConfigurationType findById(Long Id) throws Exception 
    {
        ProjectConfigurationType propertyConfigurationType = this.pctRepositry.findById(Id)
        .orElseThrow(() -> new Exception("Configuration type not found"));
        return propertyConfigurationType;
    }

    @Override
    public ProjectConfigurationType updatePropertyConfigurationTypeById(Long Id, ProjectConfigurationType pctDto)
    throws Exception 
    {
        ProjectConfigurationType propertyConfigurationType = this.pctRepositry.findById(Id)
        .orElseThrow(() -> new Exception("Configuration type not found"));
        propertyConfigurationType.setUpdatedDate(new Date().getTime());
        Optional.ofNullable(pctDto.getConfigurationTypeName().toUpperCase()).ifPresent(propertyConfigurationType::setConfigurationTypeName);
        Optional.ofNullable(pctDto.getPropertyType()).ifPresent(propertyConfigurationType::setPropertyType);
        return this.pctRepositry.save(propertyConfigurationType);
    }

    @Override
    public String deletePropertyConfigurationTypeById(Long Id) throws Exception 
    {
        ProjectConfigurationType propertyConfigurationType = this.pctRepositry.findById(Id)
        .orElseThrow(() -> new Exception("Configuration type not found"));
        this.pctRepositry.delete(propertyConfigurationType);
        String message = "Configuration type deleted successfully";
        return message;
    }

    @Override
    public List<ProjectConfigurationType> getAllPropertyConfigurationType(PropertyType propertyType) 
    {
        List<ProjectConfigurationType> propertyConfigurationTypes = this.pctRepositry.findAll();
        
        List<ProjectConfigurationType> filteredTypes = propertyConfigurationTypes.stream()
        .filter(pr -> propertyType == null || pr.getPropertyType().equals(propertyType))
        .collect(Collectors.toMap(
            pr -> createUniqueKey(pr), pr -> pr,
            (existing, replacement) -> existing, LinkedHashMap::new))
        .values()
        .stream()
        .sorted(Comparator.comparing(ProjectConfigurationType::getId).reversed()) 
        .collect(Collectors.toList());
        return filteredTypes;
    }

    private String createUniqueKey(ProjectConfigurationType configType) 
    {
        String normalizedName = normalizeConfigurationTypeName(configType.getConfigurationTypeName());
        return normalizedName + "|" + configType.getPropertyType();
    }

    private String normalizeConfigurationTypeName(String name) 
    {
        if (name == null) 
        {
            return "";
        }
        return name.toLowerCase()
                .replaceAll("\\s+", "") 
                .replaceAll("s$", "")   
                .replaceAll("es$", "");
    }
    
}
