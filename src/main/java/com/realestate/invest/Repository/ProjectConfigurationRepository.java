package com.realestate.invest.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.realestate.invest.Model.ProjectConfiguration;

public interface ProjectConfigurationRepository extends JpaRepository<ProjectConfiguration, Long>
{
    ProjectConfiguration findByProjectConfigurationNameAndConfigurationTypeId(String name, Long condifgTypeId);

    ProjectConfiguration findByProjectConfigurationNameAndConfigurationTypeConfigurationTypeName(String name, String condifgTypeName);

    ProjectConfiguration findByConfigurationTypeConfigurationTypeNameIgnoreCase(String configTypeName);

    List<ProjectConfiguration> findByConfigurationTypeConfigurationTypeName(String name);

}
