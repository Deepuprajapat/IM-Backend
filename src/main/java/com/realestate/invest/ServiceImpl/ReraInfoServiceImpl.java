package com.realestate.invest.ServiceImpl;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Model.ReraInfo;
import com.realestate.invest.Model.User;
import com.realestate.invest.Repository.ProjectRepository;
import com.realestate.invest.Repository.ReraInfoReposiotry;
import com.realestate.invest.Repository.UserRepository;
import com.realestate.invest.Service.ReraInfoService;

@Service
public class ReraInfoServiceImpl implements ReraInfoService
{

    @Autowired
    private ReraInfoReposiotry reraRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ReraInfo saveNew(ReraInfo reraDTO) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByPhone(userDetails.getUsername());
        if(user == null) throw new Exception("Please login again");

        Project project = this.projectRepository.findById(reraDTO.getProjectId()).orElseThrow(() -> new Exception("Project not found"));

        ReraInfo existingRera = this.reraRepository.findByProjectAndReraNumber(project, reraDTO.getReraNumber());
        if(existingRera != null) throw new Exception("Rera is already exist for this "+project.getProjectName());

        ReraInfo reraInfo = new ReraInfo();
        reraInfo.setCreatedOn(new Date().getTime());
        reraInfo.setPhase(reraDTO.getPhase());
        reraInfo.setProjectReraName(reraDTO.getProjectReraName());
        reraInfo.setQrImages(reraDTO.getQrImages());
        reraInfo.setReraNumber(reraDTO.getReraNumber());
        reraInfo.setStatus(reraDTO.getStatus());
        reraInfo.setProject(project);
        reraInfo.setUser(user);
        return this.reraRepository.save(reraInfo);
    }

    @Override
    public ReraInfo findById(Long Id) throws Exception 
    {
        ReraInfo reraInfo =  this.reraRepository.findById(Id).orElseThrow(() -> new Exception("Rera not found"));
        return reraInfo;
    }

    @Override
    public ReraInfo updateById(Long Id, ReraInfo reraDTO) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByPhone(userDetails.getUsername());
        if(user == null) throw new Exception("Please login again");

        ReraInfo reraInfo =  this.reraRepository.findById(Id).orElseThrow(() -> new Exception("Rera not found"));
        reraInfo.setUpdatedOn(new Date().getTime());
        reraInfo.setUser(user);
        if(reraDTO.getProjectId() != null)
        {
            Project project = this.projectRepository.findById(reraDTO.getProjectId())
            .orElseThrow(() -> new Exception("Project not found"));
            reraInfo.setProject(project);
        }
        if(reraDTO.getPhase() != null || !reraDTO.getPhase().isEmpty())
        {
            List<String> updatedPhase = reraInfo.getPhase();
            updatedPhase.addAll(reraDTO.getPhase());
            reraInfo.setPhase(updatedPhase);
        }
        Optional.ofNullable(reraDTO.getProjectReraName()).ifPresent(reraInfo::setProjectReraName);
        Optional.ofNullable(reraDTO.getQrImages()).ifPresent(reraInfo::setQrImages);
        Optional.ofNullable(reraDTO.getReraNumber()).ifPresent(reraInfo::setReraNumber);
        Optional.ofNullable(reraDTO.getStatus()).ifPresent(reraInfo::setStatus);
        return this.reraRepository.save(reraInfo);
    }

    @Override
    public String deleteById(Long Id) throws Exception 
    {
        ReraInfo reraInfo =  this.reraRepository.findById(Id).orElseThrow(() -> new Exception("Rera not found"));
        this.reraRepository.delete(reraInfo);
        return  "Rera deleted successfully";
    }

    @Override
    public List<ReraInfo> getAll(Long projectId, Long startDate, Long endDate) throws Exception 
    {
        List<ReraInfo> reraInfos = this.reraRepository.findAll();
        List<ReraInfo> filteredReraInfo = reraInfos.stream()
        .filter(rera -> projectId == null || rera.getProject().getId().equals(projectId))
        .filter(rera -> startDate == null || rera.getCreatedOn() >= startDate)
        .filter(rera -> endDate == null || rera.getCreatedOn() <= endDate)
        .sorted(Comparator.comparing(ReraInfo::getCreatedOn).reversed()).toList();
        System.out.println("Rera All Size : "+filteredReraInfo.size());
        return filteredReraInfo;
    }

    @Override
    public List<ReraInfo> findByProject(Long projectId) throws Exception 
    {
        Project project = this.projectRepository.findById(projectId).orElseThrow(() -> new Exception("Project not found"));
        List<ReraInfo> reraInfos = this.reraRepository.findByProject(project);
        return reraInfos;
    }
    
}
