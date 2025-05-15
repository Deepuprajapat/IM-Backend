package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.Model.ProjectConfiguration;

@Component
public interface ProjectConfigurationService 
{
    ProjectConfiguration saveNewProjectConfigurationByType(Long configTypeId, ProjectConfiguration pCofigDTO) throws Exception;

    ProjectConfiguration getById(Long Id) throws Exception;

    ProjectConfiguration updateById(Long Id, Long configTypeId, ProjectConfiguration pCofigDTO) throws Exception;

    String deleteById(Long Id) throws Exception;

    List<ProjectConfiguration> getAll(Long configTypeId) throws Exception;
    
}
