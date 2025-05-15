package com.realestate.invest.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Model.User;

public interface ProjectRepository extends JpaRepository <Project, Long>, JpaSpecificationExecutor<Project>
{
    
    Project findByProjectName(String name);

    Project findByProjectNameAndLocalityId(String name, Long localityId);

    List<Project> findByProjectNameContainingIgnoreCase(String name);

    Project findByProjectUrl(String projectUrl);
    
    List<Project> findByIdIn(List<Long> projectIds);

    List<Project> findByUser(User user);

}
