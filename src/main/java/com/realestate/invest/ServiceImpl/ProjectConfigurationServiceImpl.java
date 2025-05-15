package com.realestate.invest.ServiceImpl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.realestate.invest.Model.ProjectConfiguration;
import com.realestate.invest.Model.ProjectConfigurationType;
import com.realestate.invest.Repository.ProjectConfigurationRepository;
import com.realestate.invest.Repository.ProjectConfigurationTypeRepository;
import com.realestate.invest.Service.ProjectConfigurationService;

@Service
public class ProjectConfigurationServiceImpl implements ProjectConfigurationService
{

    @Autowired 
    private ProjectConfigurationRepository configurationRepository;

    @Autowired
    private ProjectConfigurationTypeRepository configurationTypeRepository;

    @Override
    public ProjectConfiguration saveNewProjectConfigurationByType(Long configTypeId, ProjectConfiguration pCofigDTO) throws Exception 
    {
        ProjectConfigurationType pConfigType = this.configurationTypeRepository.findById(configTypeId).orElseThrow(() -> 
        new Exception("Configuration type not found"));

        ProjectConfiguration existing = this.configurationRepository.findByProjectConfigurationNameAndConfigurationTypeId
        (pCofigDTO.getProjectConfigurationName(), configTypeId);
        if(existing != null) throw new Exception(existing.getProjectConfigurationName()+" is already exist for "+pConfigType.getConfigurationTypeName());

        ProjectConfiguration projectConfiguration = new ProjectConfiguration();
        projectConfiguration.setCreatedDate(new Date().getTime());
        projectConfiguration.setProjectConfigurationName(pCofigDTO.getProjectConfigurationName());
        projectConfiguration.setConfigurationType(pConfigType);
        return this.configurationRepository.save(projectConfiguration);
    }

    @Override
    public ProjectConfiguration getById(Long Id) throws Exception 
    {
        ProjectConfiguration projectConfiguration = this.configurationRepository.findById(Id).orElseThrow(() -> 
        new Exception("Project configuration not found"));
        return projectConfiguration;
    }

    @Override
    public ProjectConfiguration updateById(Long Id, Long configTypeId, ProjectConfiguration pCofigDTO) throws Exception 
    {
        ProjectConfiguration projectConfiguration = this.configurationRepository.findById(Id).orElseThrow(() -> 
        new Exception("Project configuration not found"));

        projectConfiguration.setUpdatedDate(new Date().getTime());
        Optional.ofNullable(pCofigDTO.getProjectConfigurationName()).ifPresent(projectConfiguration::setProjectConfigurationName);
        if(configTypeId != null)
        {    
            ProjectConfigurationType pConfigType = this.configurationTypeRepository.findById(configTypeId).orElseThrow(() -> 
            new Exception("Configuration type not found"));
            projectConfiguration.setConfigurationType(pConfigType);
        }
        return this.configurationRepository.save(projectConfiguration);
    }

    @Override
    public String deleteById(Long Id) throws Exception 
    {
        ProjectConfiguration projectConfiguration = this.configurationRepository.findById(Id).orElseThrow(() -> 
        new Exception("Project configuration not found"));
        this.configurationRepository.delete(projectConfiguration);
        String message = "Project configuration deleted successfully";
        return message;
    }

    @Override
    public List<ProjectConfiguration> getAll(Long configTypeId) throws Exception 
    {
        List<ProjectConfiguration> projectConfigurations = this.configurationRepository.findAll();
        
        List<ProjectConfiguration> filteredConfiguration = projectConfigurations.stream()
        .filter(fl -> configTypeId == null || fl.getConfigurationType().getId().equals(configTypeId))
        .collect(Collectors.toMap(fl -> normalizeKey(fl.getProjectConfigurationName()), 
        fl -> fl,
        (existing, replacement) -> existing, LinkedHashMap::new))
        .values()
        .stream()
        .collect(Collectors.toList());
        return filteredConfiguration;
    }

    private String normalizeKey(String key) 
    {
        return key.replaceAll("\\s+", "").trim(); // Remove all spaces
    }
    
}
