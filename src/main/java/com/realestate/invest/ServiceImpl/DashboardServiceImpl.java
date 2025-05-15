package com.realestate.invest.ServiceImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.realestate.invest.Model.Floorplan;
import com.realestate.invest.Model.Leads;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Model.Property;
import com.realestate.invest.Model.User;
import com.realestate.invest.Repository.FloorPlanRepository;
import com.realestate.invest.Repository.LeadsRepository;
import com.realestate.invest.Repository.ProjectRepository;
import com.realestate.invest.Repository.PropertyRepository;
import com.realestate.invest.Repository.UserRepository;
import com.realestate.invest.Service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired 
    private LeadsRepository leadsRepository;

    @Autowired
    private FloorPlanRepository floorplanRepository;

    @Override
    public Map<String, Object> getDashboardData(Long startDate, Long endDate) throws Exception 
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByMobilePhone(userDetails.getUsername());
        if(user == null) throw new Exception("Authentication failed");
        
        boolean isAdmin =  user.getUserRoles().stream().anyMatch(role -> role.getRole().getRoleName().equals("ADMIN"));

        return isAdmin ? getAdminDashboardData(user, startDate, endDate) : getUserDashboardData(user, startDate, endDate);
    }

    public Map<String, Object> getAdminDashboardData(User user, Long startDate, Long endDate) throws Exception 
    {
        LocalDate localDate = LocalDate.now();
        Long dayFirstDate = localDate.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();    

        Map<String, Object> dashboardData = new HashMap<>();

        List<Project> totalProjects = this.projectRepository.findByUser(user).stream()
        .filter(project -> startDate == null || project.getCreatedDate() >= startDate)
        .filter(project -> endDate == null || project.getCreatedDate() <= endDate)
        .collect(Collectors.toList());
        Long totalProjectCount = totalProjects.stream().count();
        
        List<Project> incompleteProjects = totalProjects.stream()
        .filter(project -> this.floorplanRepository.findByProject(project).isEmpty())
        .collect(Collectors.toList());
        Long incompleteProjectCount = (long) incompleteProjects.size();
        Long completedProjectCount = totalProjectCount - incompleteProjectCount;
        
        List<Project> currentMonthProjects = this.projectRepository.findByUser(user).stream()
        .filter(project -> dayFirstDate == null || project.getCreatedDate() >= dayFirstDate)
        .collect(Collectors.toList());
        Long currentMonthProjectCount = currentMonthProjects.stream().count();

        List<Project> upcomingReraProjects = this.projectRepository.findByUser(user).stream()
        .filter(project -> project.getProjectRera() == null || project.getProjectRera().isEmpty())
        .collect(Collectors.toList());
        Long upcomingReraProjectCount = (long) upcomingReraProjects.size();

        List<Floorplan> currentMonthFloorplan = this.floorplanRepository.findByUser(user).stream()
        .filter(floorplan -> dayFirstDate == null || floorplan.getCreatedDate() >= dayFirstDate)
        .collect(Collectors.toList());
        Long currentMonthFloorplanCount = (long) currentMonthFloorplan.size();

        List<Property> totalProperties = this.propertyRepository.findByUser(user).stream()
        .filter(property -> startDate == null || property.getCreatedDate() >= startDate)
        .filter(property -> endDate == null || property.getCreatedDate() <= endDate)
        .collect(Collectors.toList());
        Long totalPropertiesCount = (long) totalProperties.size();

        List<Property> projectProperties = totalProperties.stream()
        .filter(property -> property.getProject() == null) 
        .collect(Collectors.toList());
        Long projectPropertiesCount = (long) projectProperties.size();

        Long independentPropertiesCount = totalPropertiesCount - projectPropertiesCount;

        List<Property> reraProperties = totalProperties.stream()
        .filter(property -> property.getRera() == null || property.getRera().isEmpty())
        .collect(Collectors.toList());
        Long reraPropertiesCount = (long) reraProperties.size();

        List<Property> currentMonthProperties = this.propertyRepository.findByUser(user).stream()
        .filter(property -> dayFirstDate == null || property.getCreatedDate() >= dayFirstDate)
        .collect(Collectors.toList());
        Long currentMonthPropertiesCount = (long) currentMonthProperties.size();
        
        List<Leads> projectLeads = this.leadsRepository.findByProjectLeads(totalProjects.stream()
        .map(Project::getProjectName).collect(Collectors.toList()));
        Long projectLeadsCount = (long) projectLeads.size();
        
        List<Leads> propertyLeads = this.leadsRepository.findByPropertyIn(totalProperties);
        Long propertyLeadsCount = (long) propertyLeads.size();

        List<Leads> organicLeadsQuery = this.leadsRepository.findAll().stream()
        .filter(lead -> lead.getProjectName() == null || lead.getProjectName().isEmpty())
        .filter(lead -> lead.getProperty() == null)
        .collect(Collectors.toList());
        Long organicLeadsQueryCount = (long) organicLeadsQuery.size();

        List<Leads> totalLeads = Stream.concat(propertyLeads.stream(), projectLeads.stream()).distinct() 
        .collect(Collectors.toList());
        Long totalLeadsCount = (long) totalLeads.size(); 
        
        List<Leads> currentMonthProjectLeads = projectLeads.stream()
        .filter(lead -> dayFirstDate == null || lead.getCreatedDate() >= dayFirstDate)
        .collect(Collectors.toList());
        Long currentMonthProjectsLeadsCount = (long) currentMonthProjectLeads.size();  

        List<Leads> currentMonthPropertyLeads = propertyLeads.stream() 
        .filter(lead -> dayFirstDate == null || lead.getCreatedDate() >= dayFirstDate)
        .collect(Collectors.toList());
        Long currentMonthPropertyLeadsCount = (long) currentMonthPropertyLeads.size();

        List<Leads> currentMonthTotalLeads = Stream.concat(currentMonthProjectLeads.stream(), currentMonthPropertyLeads.stream())
        .distinct().collect(Collectors.toList());
        Long currentMonthTotalLeadsCount = (long) currentMonthTotalLeads.size();

        Map<String, Long> currentMonthTopProjectLeads = currentMonthTotalLeads.stream()
        .filter(lead -> lead.getProjectName() != null) 
        .flatMap(lead -> lead.getProjectName().stream()) 
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<String, Long> currentMonthTopPropertyLeads = currentMonthTotalLeads.stream()
        .filter(lead -> lead.getProperty() != null) 
        .collect(Collectors.groupingBy(lead -> lead.getProperty().getPropertyName(), Collectors.counting()));

        List<String> currentMonthTop3Projects = currentMonthTopProjectLeads.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())) 
        .limit(3)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());

        List<String> currentMonthTop3Properties = currentMonthTopPropertyLeads.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())) 
        .limit(3)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());

        Map<String, Long> allTimeTopProjectLeads = totalLeads.stream()
        .filter(lead -> lead.getProjectName() != null) 
        .flatMap(lead -> lead.getProjectName().stream()) 
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));    

        Map<String, Long> allTimeTopPropertyLeads = totalLeads.stream()
        .filter(lead -> lead.getProperty() != null) 
        .collect(Collectors.groupingBy(lead -> lead.getProperty().getPropertyName(), Collectors.counting()));

        List<String> allTimeTop3Projects = allTimeTopProjectLeads.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())) 
        .limit(3)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());

        List<String> allTimeTop3Properties = allTimeTopPropertyLeads.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())) 
        .limit(3)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());


        List<Leads> allLeads = this.leadsRepository.findAll();
        Map<String, Long> quarterlyData = new LinkedHashMap<>();
        Map<String, Long> halfYearlyData = new LinkedHashMap<>();
        Map<String, Long> annualData = new LinkedHashMap<>();

        LocalDate today = LocalDate.now();
        LocalDate sixMonthsAgo = today.minusMonths(5);  
        LocalDate twelveMonthsAgo = today.minusMonths(11);  

        for (Leads lead : allLeads) 
        {
            LocalDate leadDate = Instant.ofEpochMilli(lead.getCreatedDate()).atZone(ZoneId.systemDefault()).toLocalDate();
            String monthKey = leadDate.getMonth().name().substring(0, 3);  

            if (!leadDate.isBefore(today.minusMonths(2))) 
            {
                quarterlyData.put(monthKey, quarterlyData.getOrDefault(monthKey, 0L) + 1);
            }

            if (!leadDate.isBefore(sixMonthsAgo)) 
            {
                halfYearlyData.put(monthKey, halfYearlyData.getOrDefault(monthKey, 0L) + 1);
            }

            if (!leadDate.isBefore(twelveMonthsAgo)) 
            {
                annualData.put(monthKey, annualData.getOrDefault(monthKey, 0L) + 1);
            }
        }


        dashboardData.put("totalProjects", totalProjects);
        dashboardData.put("totalProjectCount", totalProjectCount);
        dashboardData.put("incompleteProjects", incompleteProjects);
        dashboardData.put("incompleteProjectCount", incompleteProjectCount);
        dashboardData.put("completedProjectCount", completedProjectCount);
        dashboardData.put("currentMonthProjects", currentMonthProjects);
        dashboardData.put("currentMonthProjectCount", currentMonthProjectCount);
        dashboardData.put("upcomingReraProjects", upcomingReraProjects);
        dashboardData.put("upcomingReraProjectCount", upcomingReraProjectCount);
        dashboardData.put("currentMonthFloorPlan", currentMonthFloorplan);
        dashboardData.put("currentMonthFloorPlanCount", currentMonthFloorplanCount);
        dashboardData.put("totalProperties", totalProperties);
        dashboardData.put("totalPropertiesCount", totalPropertiesCount);
        dashboardData.put("projectProperties", projectProperties);
        dashboardData.put("projectPropertiesCount", projectPropertiesCount);
        dashboardData.put("independentPropertiesCount", independentPropertiesCount);
        dashboardData.put("reraProperties", reraProperties);
        dashboardData.put("reraPropertiesCount", reraPropertiesCount);
        dashboardData.put("currentMonthProperties", currentMonthProperties);
        dashboardData.put("currentMonthPropertiesCount", currentMonthPropertiesCount);
        dashboardData.put("projectLeads", projectLeads);
        dashboardData.put("projectLeadsCount", projectLeadsCount);
        dashboardData.put("propertyLeads", propertyLeads);
        dashboardData.put("propertyLeadsCount", propertyLeadsCount);
        dashboardData.put("totalLeads", totalLeads);
        dashboardData.put("organicLeadsQuery", organicLeadsQuery);
        dashboardData.put("organicLeadsQueryCount", organicLeadsQueryCount);
        dashboardData.put("totalLeadsCount", totalLeadsCount);
        dashboardData.put("currentMonthProjectLeads", currentMonthProjectLeads);
        dashboardData.put("currentMonthProjectLeadsCount", currentMonthProjectsLeadsCount);
        dashboardData.put("currentMonthPropertyLeads", currentMonthPropertyLeads);
        dashboardData.put("currentMonthPropertyLeadsCount", currentMonthPropertyLeadsCount);
        dashboardData.put("currentMonthLeads", currentMonthTotalLeads);
        dashboardData.put("currentMonthLeadsCount", currentMonthTotalLeadsCount);
        dashboardData.put("currentMonthTop3Projects", currentMonthTop3Projects);
        dashboardData.put("currentMonthTop3Properties", currentMonthTop3Properties);
        dashboardData.put("currentMonthTopProjectLeads", currentMonthTopProjectLeads);
        dashboardData.put("currentMonthToppropertyLeads", currentMonthTopPropertyLeads);
        dashboardData.put("allTimeTop3Projects", allTimeTop3Projects);
        dashboardData.put("allTimeTop3Properties", allTimeTop3Properties);
        dashboardData.put("allTimeTopProjectLeads", allTimeTopProjectLeads);
        dashboardData.put("allTimeToppropertyLeads", allTimeTopPropertyLeads);
        dashboardData.put("leadCountsQuarterly", quarterlyData);
        dashboardData.put("leadCountsHalfYearly", halfYearlyData);
        dashboardData.put("leadCountsAnnually", annualData);
        
        return dashboardData;
    }

    public Map<String, Object> getUserDashboardData(User user, Long startDate, Long endDate) throws Exception 
    {
        
        LocalDate localDate = LocalDate.now();
        Long dayFirstDate = localDate.withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();    

        Map<String, Object> dashboardData = new HashMap<>();

        List<Property> totalProperties = this.propertyRepository.findByUser(user).stream()
        .filter(property -> startDate == null || property.getCreatedDate() >= startDate)
        .filter(property -> endDate == null || property.getCreatedDate() <= endDate)
        .collect(Collectors.toList());
        Long totalPropertiesCount = (long) totalProperties.size();

        List<Property> projectProperties = totalProperties.stream()
        .filter(property -> property.getProject() == null) 
        .collect(Collectors.toList());
        Long projectPropertiesCount = (long) projectProperties.size();

        Long independentPropertiesCount = totalPropertiesCount - projectPropertiesCount;

        List<Property> reraProperties = totalProperties.stream()
        .filter(property -> property.getRera() == null || property.getRera().isEmpty())
        .collect(Collectors.toList());
        Long reraPropertiesCount = (long) reraProperties.size();

        List<Property> currentMonthProperties = this.propertyRepository.findByUser(user).stream()
        .filter(property -> dayFirstDate == null || property.getCreatedDate() >= dayFirstDate)
        .collect(Collectors.toList());
        Long currentMonthPropertiesCount = (long) currentMonthProperties.size();
        
        List<Leads> propertyLeads = this.leadsRepository.findByPropertyIn(totalProperties);
        Long propertyLeadsCount = (long) propertyLeads.size();
        
        List<Leads> currentMonthPropertyLeads = propertyLeads.stream() 
        .filter(lead -> dayFirstDate == null || lead.getCreatedDate() >= dayFirstDate)
        .collect(Collectors.toList());
        Long currentMonthPropertyLeadsCount = (long) currentMonthPropertyLeads.size();

        Map<String, Long> currentMonthTopPropertyLeads = currentMonthPropertyLeads.stream()
        .filter(lead -> lead.getProperty() != null) 
        .collect(Collectors.groupingBy(lead -> lead.getProperty().getPropertyName(), Collectors.counting()));

        List<String> currentMonthTop3Properties = currentMonthTopPropertyLeads.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())) 
        .limit(3)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());

        Map<String, Long> allTimeTopPropertyLeads = propertyLeads.stream()
        .filter(lead -> lead.getProperty() != null) 
        .collect(Collectors.groupingBy(lead -> lead.getProperty().getPropertyName(), Collectors.counting()));

        List<String> allTimeTop3Properties = allTimeTopPropertyLeads.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())) 
        .limit(3)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());

        List<Leads> allLeads = propertyLeads;
        Map<String, Long> quarterlyData = new LinkedHashMap<>();
        Map<String, Long> halfYearlyData = new LinkedHashMap<>();
        Map<String, Long> annualData = new LinkedHashMap<>();

        LocalDate today = LocalDate.now();
        LocalDate sixMonthsAgo = today.minusMonths(5);  
        LocalDate twelveMonthsAgo = today.minusMonths(11);  

        for (Leads lead : allLeads) 
        {
            LocalDate leadDate = Instant.ofEpochMilli(lead.getCreatedDate()).atZone(ZoneId.systemDefault()).toLocalDate();
            String monthKey = leadDate.getMonth().name().substring(0, 3);  

            if (!leadDate.isBefore(today.minusMonths(2))) 
            {
                quarterlyData.put(monthKey, quarterlyData.getOrDefault(monthKey, 0L) + 1);
            }

            if (!leadDate.isBefore(sixMonthsAgo)) 
            {
                halfYearlyData.put(monthKey, halfYearlyData.getOrDefault(monthKey, 0L) + 1);
            }

            if (!leadDate.isBefore(twelveMonthsAgo)) 
            {
                annualData.put(monthKey, annualData.getOrDefault(monthKey, 0L) + 1);
            }
        }

        dashboardData.put("totalProperties", totalProperties);
        dashboardData.put("totalPropertiesCount", totalPropertiesCount);
        dashboardData.put("projectProperties", projectProperties);
        dashboardData.put("projectPropertiesCount", projectPropertiesCount);
        dashboardData.put("independentPropertiesCount", independentPropertiesCount);
        dashboardData.put("reraProperties", reraProperties);
        dashboardData.put("reraPropertiesCount", reraPropertiesCount);
        dashboardData.put("currentMonthProperties", currentMonthProperties);
        dashboardData.put("currentMonthPropertiesCount", currentMonthPropertiesCount);
        dashboardData.put("totalLeads", propertyLeads);
        dashboardData.put("totalLeadsCount", propertyLeadsCount);
        dashboardData.put("currentMonthTotalLeads", currentMonthPropertyLeads);
        dashboardData.put("currentMonthTotalLeadsCount", currentMonthPropertyLeadsCount);
        dashboardData.put("currentMonthTop3Properties", currentMonthTop3Properties);
        dashboardData.put("currentMonthToppropertyLeads", currentMonthTopPropertyLeads);
        dashboardData.put("allTimeTop3Properties", allTimeTop3Properties);
        dashboardData.put("allTimeToppropertyLeads", allTimeTopPropertyLeads);
        dashboardData.put("leadCountsQuarterly", quarterlyData);
        dashboardData.put("leadCountsHalfYearly", halfYearlyData);
        dashboardData.put("leadCountsAnnually", annualData);
        
        return dashboardData;
    }
    
}
