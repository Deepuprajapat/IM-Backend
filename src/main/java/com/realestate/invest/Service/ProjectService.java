package com.realestate.invest.Service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.realestate.invest.DTOModel.ProjectDTO;
import com.realestate.invest.EnumAndJsons.ProjectInfo;
import com.realestate.invest.EnumAndJsons.ProjectStatus;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.Model.Faq;
import com.realestate.invest.Model.PaymentPlan;
import com.realestate.invest.Model.Project;

@Component
public interface ProjectService 
{
    
    Project saveNewProject(ProjectDTO projectDTO) throws Exception;

    Project findById(Long Id) throws Exception;

    List<Project> findByName(String name) throws Exception;

    Project updateProjectById(Long Id, ProjectDTO projectDto) throws Exception;

    String deleteProjectById(Long Id) throws Exception;

    Page<Project> getAllProjects(Boolean isPriority, Boolean isPremium, Boolean isFeatured, Boolean isDeleted, ProjectStatus status, 
    Long developerId, Long cityId, Long localityId, String name, PropertyType type, List<String> configurations, Pageable pageable) throws Exception;

    Project findByurl(String url) throws Exception;
    
    List<Project> findByIdIn(List<Long> projectIds) throws Exception;

    List<ProjectInfo> getProjectInfos(Boolean isDeleted) throws Exception;

    List<PaymentPlan> getAllPayPlans(Long projectId) throws Exception;

    List<Faq> getAllFaqs(Long projectId) throws Exception;

}
