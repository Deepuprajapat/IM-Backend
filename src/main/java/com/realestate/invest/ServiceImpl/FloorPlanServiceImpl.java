package com.realestate.invest.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.realestate.invest.DTOModel.FloorPlanDTO;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.Model.Floorplan;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Model.ProjectConfiguration;
import com.realestate.invest.Model.User;
import com.realestate.invest.Repository.FloorPlanRepository;
import com.realestate.invest.Repository.ProjectRepository;
import com.realestate.invest.Repository.UserRepository;
import com.realestate.invest.Repository.ProjectConfigurationRepository;
import com.realestate.invest.Service.FloorPlanService;

@Service
public class FloorPlanServiceImpl implements FloorPlanService
{

    @Autowired 
    private FloorPlanRepository floorPlanRepository;

    @Autowired
    private ProjectConfigurationRepository pCTRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Floorplan saveNewFloorplanByProject(Long projectId, FloorPlanDTO floorPlanDTO) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByMobilePhone(userDetails.getUsername());
        if(user == null) throw new Exception("Authentication failed");

        Project project = this.projectRepository.findById(projectId).orElseThrow(() -> new Exception("Project not found"));
        ProjectConfiguration propertyConfiguration = this.pCTRepository.findById(floorPlanDTO.getPropertyConfigurationId())
        .orElseThrow(() -> new Exception("Configuration not found"));
        Floorplan floorplan = new Floorplan();
        floorplan.setCreatedDate(new Date().getTime());
        floorplan.setTitle(floorPlanDTO.getTitle());
        floorplan.setSize(floorPlanDTO.getSize());
        floorplan.setImgUrl(floorPlanDTO.getImgUrl());
        floorplan.setPrice(floorPlanDTO.getPrice());
        floorplan.setConfiguration(propertyConfiguration);
        floorplan.setProject(project);
        floorplan.setSoldOut(floorPlanDTO.getIsSoldOut());
        floorplan.setUser(user);
        return this.floorPlanRepository.save(floorplan);
    }

    @Override
    public Floorplan getById(Long Id) throws Exception 
    {
        Floorplan floorplan = this.floorPlanRepository.findById(Id).orElseThrow(
        () -> new Exception("Floor plan not found"));
        return floorplan;
    }

    @Override
    public Floorplan updateById(Long Id, FloorPlanDTO floorPlanDTO) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByMobilePhone(userDetails.getUsername());
        if(user == null) throw new Exception("Authentication failed");

        Floorplan floorplan = this.floorPlanRepository.findById(Id).orElseThrow(
        () -> new Exception("Floor plan not found"));
        
        floorplan.setUpdatedDate(new Date().getTime());
        Optional.ofNullable(floorPlanDTO.getTitle()).ifPresent(floorplan::setTitle);
        Optional.ofNullable(floorPlanDTO.getImgUrl()).ifPresent(floorplan::setImgUrl);
        if(floorPlanDTO.getSize() != null) floorplan.setSize(floorPlanDTO.getSize());
        if(floorPlanDTO.getPrice() != 0) floorplan.setPrice(floorPlanDTO.getPrice());
        if(floorPlanDTO.getIsSoldOut() !=  null) floorplan.setSoldOut(floorPlanDTO.getIsSoldOut());

        if (floorPlanDTO.getProjectId() != null) 
        {
            Project project = this.projectRepository.findById(floorPlanDTO.getProjectId())
                .orElseThrow(() -> new Exception("Project not found"));
            if (!project.equals(floorplan.getProject())) floorplan.setProject(project);
        }

        if (floorPlanDTO.getPropertyConfigurationId() != null) 
        {
            ProjectConfiguration propertyConfiguration = this.pCTRepository.findById(floorPlanDTO.getPropertyConfigurationId())
                .orElseThrow(() -> new Exception("Configuration not found"));
            if (!propertyConfiguration.equals(floorplan.getConfiguration())) floorplan.setConfiguration(propertyConfiguration);
        }

        floorplan.setUser(user);
        return this.floorPlanRepository.save(floorplan);
    }

    @Override
    public String deleteFloorPlanById(Long Id) throws Exception 
    {
        Floorplan floorplan = this.floorPlanRepository.findById(Id).orElseThrow(
        () -> new Exception("Floor plan not found"));
        this.floorPlanRepository.delete(floorplan);
        String message = "Floor plan deleted successfully";
        return message;
    }

    @Override
    public List<Floorplan> getAllFloorplans(Long projectId, Long configurationId, Long configTypeId, PropertyType propertyType)
    throws Exception 
    {
        List<Floorplan> floorplans = this.floorPlanRepository.findAll();
        List<Floorplan> filteredFloorplans = floorplans.stream().filter( fl -> 
        (projectId == null || fl.getProject().getId().equals(projectId)) &&
        (configurationId == null || fl.getConfiguration().getId().equals(configurationId)) &&
        (configTypeId == null || fl.getConfiguration().getConfigurationType().getId().equals(configTypeId)) &&
        (propertyType == null || fl.getConfiguration().getConfigurationType().getPropertyType().equals(propertyType)))
        .collect(Collectors.toList());
        return filteredFloorplans;
    }

    @Override
    public List<Floorplan> findByProject(Long projectId) throws Exception 
    
    {
        Project project = this.projectRepository.findById(projectId).orElseThrow(() -> 
        new Exception("Project Not Found"));
        List<Floorplan> floorplans = this.floorPlanRepository.findByProject(project);
        if(floorplans == null)
        {
            return null;
        }
        return floorplans;
    }
    
}
