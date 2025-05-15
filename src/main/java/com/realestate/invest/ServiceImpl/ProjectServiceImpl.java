package com.realestate.invest.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.realestate.invest.DTOModel.ProjectDTO;
import com.realestate.invest.EnumAndJsons.ProjectInfo;
import com.realestate.invest.EnumAndJsons.ProjectStatus;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.Model.Amenities;
import com.realestate.invest.Model.Developer;
import com.realestate.invest.Model.Faq;
import com.realestate.invest.Model.Locality;
import com.realestate.invest.Model.PaymentPlan;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Model.ProjectConfigurationType;
import com.realestate.invest.Model.ProjectImage;
import com.realestate.invest.Model.User;
import com.realestate.invest.Repository.AmenitiesRepository;
import com.realestate.invest.Repository.DeveloperRepository;
import com.realestate.invest.Repository.FaqRepository;
import com.realestate.invest.Repository.LocalityRepository;
import com.realestate.invest.Repository.PaymentPlanRepository;
import com.realestate.invest.Repository.ProjectConfigurationTypeRepository;
import com.realestate.invest.Repository.ProjectRepository;
import com.realestate.invest.Repository.UserRepository;
import com.realestate.invest.Service.ProjectService;
import com.realestate.invest.Utils.ProjectsUtility;

@Service
public class ProjectServiceImpl implements ProjectService
{

    @Autowired 
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectConfigurationTypeRepository configurationTypeRepository;

    @Autowired
    private AmenitiesRepository amenitiesRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocalityRepository localityRepository;

    @Autowired
    private ProjectsUtility projectsUtility;

    @Autowired
    private PaymentPlanRepository paymentPlanRepository;

    @Autowired
    private FaqRepository faqRepository;

    @Override
    public Project saveNewProject(ProjectDTO projectDTO) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByMobilePhone(userDetails.getUsername());
        if(user == null) throw new Exception("Authentication failed");

        Project existing = this.projectRepository.findByProjectNameAndLocalityId(projectDTO.getProjectName(), projectDTO.getLocalityId());
        if(existing != null) throw new Exception(projectDTO.getProjectName()+" is already exist in same locality");

        ProjectConfigurationType projectConfigurationType = this.configurationTypeRepository
        .findById(projectDTO.getConfigurationsTypeId()).orElseThrow(() -> new Exception("Configuration type not found"));

        Developer developer = this.developerRepository.findById(projectDTO.getDeveloperId())
        .orElseThrow(()-> new Exception("Developer not found"));

        Locality locality = this.localityRepository.findById(projectDTO.getLocalityId())
        .orElseThrow(() -> new Exception("Locality not found"));

        Project project = new Project();
        project.setCreatedDate(new Date().getTime());
        project.setProjectName(projectDTO.getProjectName());
        
        if(projectDTO.getProjectUrl() != null)
        {
            Project existingUrl = this.projectRepository.findByProjectUrl(projectDTO.getProjectUrl());
            if(existingUrl != null) throw new Exception("Project URL already exist");
            project.setProjectUrl(projectDTO.getProjectUrl());
        }


        project.setProjectAddress(projectDTO.getProjectAddress());
        project.setProjectArea(projectDTO.getProjectArea());
        project.setProjectRera(projectDTO.getProjectRera());
        project.setProjectUnits(projectDTO.getProjectUnits());
        project.setProjectLaunchDate(projectDTO.getProjectLaunchDate());
        project.setProjectPossessionDate(projectDTO.getProjectPossessionDate());
        project.setTotalFloor(projectDTO.getTotalFloor());
        project.setProjectVideoCount(projectDTO.getProjectVideoCount());
        project.setProjectVideos(projectDTO.getProjectVideos());
        project.setProjectLocationUrl(projectDTO.getProjectLocationUrl());
        project.setSiteplanImg(projectDTO.getSiteplanImg());
        project.setAltSitePlanImg(projectDTO.getAltSitePlanImg());
        project.setReraLink(projectDTO.getReraLink());
        project.setAvailableUnit(projectDTO.getAvailableUnit());
        project.setPriority(projectDTO.isPriority());
        project.setFeatured(projectDTO.isFeatured());
        project.setPremium(projectDTO.isPremium());
        project.setDeleted(projectDTO.isDeleted());
        project.setProjectBrochure(projectDTO.getProjectBrochure());
        project.setProjectAbout(projectDTO.getProjectAbout());
        project.setMetaTitle(projectDTO.getMetaTitle());
        project.setMetaDesciption(projectDTO.getMetaDesciption());
        project.setShortAddress(projectDTO.getShortAddress());
        project.setProjectSchema(projectDTO.getProjectSchema());
        project.setProjectDescription(projectDTO.getProjectDescription());
        project.setUsp(projectDTO.getUsps());
        project.setMetaKeywords(projectDTO.getMetaKeywords());
        project.setProjectConfigurations(projectDTO.getProjectConfigurations());
        project.setCoverPhoto(projectDTO.getCoverPhoto());
        project.setOverviewPara(projectDTO.getOverviewPara());
        project.setFloorPara(projectDTO.getFloorPara());
        project.setPriceListPara(projectDTO.getPriceListPara());
        project.setPaymentPara(projectDTO.getPaymentPara());
        project.setAmenitiesPara(projectDTO.getAmenitiesPara());
        project.setSiteplanPara(projectDTO.getSiteplanPara());
        project.setVideoPara(projectDTO.getVideoPara());
        project.setWhyPara(projectDTO.getWhyPara());
        project.setLocationMap(projectDTO.getLocationMap());
        project.setLocationPara(projectDTO.getLocationPara());
        project.setStatus(projectDTO.getStatus());
        project.setConfigurationsType(projectConfigurationType);
        project.setTotalTowers(projectDTO.getTotalTowers());
        project.setProjectLogo(projectDTO.getProjectLogo());
        project.setAltProjectLogo(projectDTO.getAltProjectLogo());
        if(projectDTO.getProjectName() != null && projectDTO.getSiteplanImg() != null) project.setAltSitePlanImg(projectDTO.getProjectName() + projectDTO.getSiteplanImg());

        Map<String, String> newImages = new HashMap<>();
        for (ProjectImage projectImage : projectDTO.getProjectImages()) 
        {
            if (projectImage.getImagePath() != null && projectImage.getAltName() != null) 
            {
                newImages.put(projectImage.getImagePath(), projectImage.getAltName());
            }
        }
        project.setImages(newImages);

        List<Amenities> validAmenities = projectDTO.getProjectAmenities().stream()
        .map(amenitiesRepository::findById) 
        .filter(Optional::isPresent) 
        .map(Optional::get) 
        .collect(Collectors.toList());
    
        if (!validAmenities.isEmpty()) 
        {
            project.setProjectAmenities(validAmenities);
        }

        project.setLocality(locality);
        project.setDeveloper(developer);
        project.setUser(user);
        Project savedProject = this.projectRepository.save(project);
        if(projectDTO.getPaymentPlans() != null && !projectDTO.getPaymentPlans().isEmpty())
        {
            for(PaymentPlan paymentPlan : projectDTO.getPaymentPlans())
            {
                if(paymentPlan.getPaymentPlanName() != null && paymentPlan.getPaymentPlanValue() != null)
                {
                    PaymentPlan newPaymentPlan = new PaymentPlan();
                    newPaymentPlan.setPaymentPlanName(paymentPlan.getPaymentPlanName());
                    newPaymentPlan.setPaymentPlanValue(paymentPlan.getPaymentPlanValue());
                    newPaymentPlan.setProject(savedProject);
                    this.paymentPlanRepository.save(newPaymentPlan);
                }
            }
        }
        
        if(projectDTO.getFaqs() != null && !projectDTO.getFaqs().isEmpty())
        {
            for(Faq faq : projectDTO.getFaqs())
            {
                if(faq.getQuestion() != null && faq.getAnswer() != null)
                {
                    Faq newFaq = new Faq();
                    newFaq.setQuestion(faq.getQuestion());
                    newFaq.setAnswer(faq.getAnswer());
                    newFaq.setProject(savedProject);
                    this.faqRepository.save(newFaq);
                }
            }
        }
        return savedProject;
    }

    @Override
    public Project findById(Long Id) throws Exception 
    {
        Project project = this.projectRepository.findById(Id)
        .orElseThrow(() -> new Exception("Project not found"));
        return this.projectsUtility.setFloorPlan(project);
    }

    @Override
    public List<Project> findByName(String name) throws Exception 
    {
        List<Project> projects = this.projectRepository.findAll().stream()
        .filter( fl -> name == null || fl.getProjectName().toLowerCase().contains(name.toLowerCase()))
        .collect(Collectors.toList());
        return projects;
    }

    @Override
    public Project updateProjectById(Long Id, ProjectDTO projectDTO) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByMobilePhone(userDetails.getUsername());
        if(user == null) throw new Exception("Authentication failed");

        Project project = this.projectRepository.findById(Id)
        .orElseThrow(() -> new Exception("Project not found"));
        
        project.setUpdatedDate(new Date().getTime());
        if(projectDTO.getProjectUrl() != null && !projectDTO.getProjectUrl().equals(project.getProjectUrl()))
        {
            Project existingUrl = this.projectRepository.findByProjectUrl(projectDTO.getProjectUrl());
            if(existingUrl != null) throw new Exception("Project URL already exist");
            project.setProjectUrl(projectDTO.getProjectUrl());
        }
        
        Optional.ofNullable(projectDTO.getProjectName()).ifPresent(project::setProjectName);
        Optional.ofNullable(projectDTO.getProjectAddress()).ifPresent(project::setProjectAddress);
        Optional.ofNullable(projectDTO.getProjectArea()).ifPresent(project::setProjectArea);
        Optional.ofNullable(projectDTO.getProjectRera()).ifPresent(project::setProjectRera);
        Optional.ofNullable(projectDTO.getProjectUnits()).ifPresent(project::setProjectUnits);
        Optional.ofNullable(projectDTO.getProjectLaunchDate()).ifPresent(project::setProjectLaunchDate);
        Optional.ofNullable(projectDTO.getProjectPossessionDate()).ifPresent(project::setProjectPossessionDate);
        Optional.ofNullable(projectDTO.getTotalFloor()).ifPresent(project::setTotalFloor);
        Optional.ofNullable(projectDTO.getProjectVideoCount()).ifPresent(project::setProjectVideoCount);
        Optional.ofNullable(projectDTO.getProjectVideos()).ifPresent(project::setProjectVideos);
        Optional.ofNullable(projectDTO.getProjectLocationUrl()).ifPresent(project::setProjectLocationUrl);
        Optional.ofNullable(projectDTO.getSiteplanImg()).ifPresent(project::setSiteplanImg);
        Optional.ofNullable(projectDTO.getAltSitePlanImg()).ifPresent(project::setAltSitePlanImg);
        Optional.ofNullable(projectDTO.getReraLink()).ifPresent(project::setReraLink);
        Optional.ofNullable(projectDTO.getAvailableUnit()).ifPresent(project::setAvailableUnit);
        Optional.ofNullable(projectDTO.isPriority()).ifPresent(project::setPriority);
        Optional.ofNullable(projectDTO.isFeatured()).ifPresent(project::setFeatured);
        Optional.ofNullable(projectDTO.isPremium()).ifPresent(project::setPremium);
        Optional.ofNullable(projectDTO.isDeleted()).ifPresent(project::setDeleted);
        Optional.ofNullable(projectDTO.getProjectBrochure()).ifPresent(project::setProjectBrochure);
        Optional.ofNullable(projectDTO.getProjectAbout()).ifPresent(project::setProjectAbout);
        Optional.ofNullable(projectDTO.getMetaTitle()).ifPresent(project::setMetaTitle);
        Optional.ofNullable(projectDTO.getMetaDesciption()).ifPresent(project::setMetaDesciption);
        Optional.ofNullable(projectDTO.getShortAddress()).ifPresent(project::setShortAddress);
        Optional.ofNullable(projectDTO.getProjectSchema()).ifPresent(project::setProjectSchema);
        Optional.ofNullable(projectDTO.getProjectDescription()).ifPresent(project::setProjectDescription);
        Optional.ofNullable(projectDTO.getUsps()).ifPresent(project::setUsp);
        Optional.ofNullable(projectDTO.getMetaKeywords()).ifPresent(project::setMetaKeywords);
        Optional.ofNullable(projectDTO.getProjectConfigurations()).ifPresent(project::setProjectConfigurations);
        Optional.ofNullable(projectDTO.getOverviewPara()).ifPresent(project::setOverviewPara);
        Optional.ofNullable(projectDTO.getFloorPara()).ifPresent(project::setFloorPara);
        Optional.ofNullable(projectDTO.getPriceListPara()).ifPresent(project::setPriceListPara);
        Optional.ofNullable(projectDTO.getPaymentPara()).ifPresent(project::setPaymentPara);
        Optional.ofNullable(projectDTO.getAmenitiesPara()).ifPresent(project::setAmenitiesPara);
        Optional.ofNullable(projectDTO.getSiteplanPara()).ifPresent(project::setSiteplanPara);
        Optional.ofNullable(projectDTO.getVideoPara()).ifPresent(project::setVideoPara);
        Optional.ofNullable(projectDTO.getWhyPara()).ifPresent(project::setWhyPara);
        Optional.ofNullable(projectDTO.getLocationMap()).ifPresent(project::setLocationMap);
        Optional.ofNullable(projectDTO.getLocationPara()).ifPresent(project::setLocationPara);
        Optional.ofNullable(projectDTO.getStatus()).ifPresent(project::setStatus);
        Optional.ofNullable(projectDTO.getTotalTowers()).ifPresent(project::setTotalTowers);
        Optional.ofNullable(projectDTO.getAltProjectLogo()).ifPresent(project::setAltProjectLogo);
        Optional.ofNullable(projectDTO.getProjectLogo()).ifPresent(project::setProjectLogo);
        Optional.ofNullable(projectDTO.getCoverPhoto()).ifPresent(project::setCoverPhoto);

        if(!projectDTO.getProjectImages().isEmpty() && projectDTO.getProjectImages() != null)
        {
            Map<String, String> newImages = new HashMap<>();
            for (ProjectImage projectImage : projectDTO.getProjectImages()) 
            {
                if (projectImage.getImagePath() != null && projectImage.getAltName() != null) 
                {
                    newImages.put(projectImage.getImagePath(), projectImage.getAltName());
                }
            }
            project.setImages(newImages);
        }

        if(projectDTO.getProjectAmenities() != null)
        {
            List<Amenities> amenities = this.amenitiesRepository.findAllById(projectDTO.getProjectAmenities());
            if(amenities != null) project.setProjectAmenities(amenities);
        }

        if(projectDTO.getConfigurationsTypeId() != null) 
        {
            ProjectConfigurationType projectConfigurationType = this.configurationTypeRepository
            .findById(projectDTO.getConfigurationsTypeId()).orElseThrow(() -> new Exception("Configuration type not found"));
            project.setConfigurationsType(projectConfigurationType);
        }
        if(projectDTO.getDeveloperId() != null)
        {
            Developer developer = this.developerRepository.findById(projectDTO.getDeveloperId())
            .orElseThrow(()-> new Exception("Developer not found"));
            project.setDeveloper(developer);
        }
        if(projectDTO.getLocalityId() != null)
        {
            Locality locality = this.localityRepository.findById(projectDTO.getLocalityId())
            .orElseThrow(() -> new Exception("Locality not found"));
            project.setLocality(locality);
        }
        
        project.setUser(user);
        return this.projectRepository.save(project);
    }

    @Override
    public String deleteProjectById(Long Id) throws Exception 
    {
        Project project = this.projectRepository.findById(Id)
        .orElseThrow(() -> new Exception("Project not found"));
        this.projectRepository.delete(project);
        String message = "Project deleted successfully";
        return message;
    }

    @Override
    public Page<Project> getAllProjects(Boolean isPriority, Boolean isPremium, Boolean isFeatured, Boolean isDeleted,
    ProjectStatus status, Long developerId, Long cityId, Long localityId, String name, PropertyType type, 
    List<String> configurations, Pageable pageable) 
    {
        Specification<Project> spec = ProjectsUtility.filterByCriteria(isPriority, isPremium, isFeatured, isDeleted, status, 
        developerId, cityId, localityId, name, type, configurations);
        
        Page<Project> projectsPage = projectRepository.findAll(spec, pageable);
        this.projectsUtility.setFloorPlanInList(projectsPage.getContent());
        return new PageImpl<>(projectsPage.getContent(), pageable, projectsPage.getTotalElements());
    }

    @Override
    public Project findByurl(String url) throws Exception 
    {
        Project project = this.projectRepository.findByProjectUrl(url);
        if(project == null) throw new Exception("Project not found");
        if(project.isDeleted()) throw new Exception("Project not found");
        return this.projectsUtility.setFloorPlan(project);
    }

    public List<String> sanitizeInput(List<String> configurations) 
    {
        return configurations.stream()
            .map(str -> str.replaceAll("[^\\x00-\\x7F]", ""))
            .collect(Collectors.toList());
    }

    @Override
    public List<Project> findByIdIn(List<Long> projectIds) throws Exception 
    {
        List<Project> projects = this.projectRepository.findByIdIn(projectIds);
        return this.projectsUtility.setFloorPlanInList(projects);
    }

    @Override
    public List<ProjectInfo> getProjectInfos(Boolean isDeleted) throws Exception 
    {
        List<ProjectInfo> projectInfos = new ArrayList<>();
        List<Project> projects = this.projectRepository.findAll().stream()
        .filter(pr -> isDeleted == null || pr.isDeleted() == isDeleted).toList();
        for(Project project : projects)
        {
            ProjectInfo projectInfo = new ProjectInfo();
            projectInfo.setId(project.getId());
            projectInfo.setProjectName(project.getProjectName());
            projectInfo.setProjectUrl(project.getProjectUrl());
            projectInfo.setProjectLogo(project.getProjectLogo());
            projectInfos.add(projectInfo);
        }
        return projectInfos;
    }

    @Override
    public List<PaymentPlan> getAllPayPlans(Long projectId) throws Exception 
    {
        List<PaymentPlan> payPlans = this.paymentPlanRepository.findAll().stream()
        .filter(pl -> projectId == null || pl.getProject().getId() == projectId).toList();
        return payPlans;
    }

    @Override
    public List<Faq> getAllFaqs(Long projectId) throws Exception 
    {
        List<Faq> faqs = this.faqRepository.findAll().stream()
        .filter(fq -> projectId == null || fq.getProject().getId() == projectId).toList();
        return faqs;
    }

    
}
