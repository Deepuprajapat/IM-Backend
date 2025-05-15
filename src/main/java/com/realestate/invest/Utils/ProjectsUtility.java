package com.realestate.invest.Utils;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.realestate.invest.EnumAndJsons.ProjectStatus;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.Model.Faq;
import com.realestate.invest.Model.Floorplan;
import com.realestate.invest.Model.PaymentPlan;
import com.realestate.invest.Model.Project;
import com.realestate.invest.Repository.FaqRepository;
import com.realestate.invest.Repository.FloorPlanRepository;
import com.realestate.invest.Repository.PaymentPlanRepository;
import jakarta.persistence.criteria.Predicate;

@Component
public class ProjectsUtility 
{
    @Autowired
    private FloorPlanRepository floorPlanRepository;

    @Autowired
    private PaymentPlanRepository paymentPlanRepository;

    @Autowired
    private FaqRepository faqRepository;

    
    public static Specification<Project> filterByCriteria(Boolean isPriority, Boolean isPremium, Boolean isFeatured,
    Boolean isDeleted, ProjectStatus status, Long developerId, Long cityId, Long localityId, String name, 
    PropertyType type, List<String> configurations) 
    {
        return (root, query, cb) -> 
        {
            Predicate predicate = cb.conjunction();

            if (isPriority != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("isPriority"), isPriority));
            }
            if (isPremium != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("isPremium"), isPremium));
            }
            if (isFeatured != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("isFeatured"), isFeatured));
            }
            if (isDeleted != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("isDeleted"), isDeleted));
            }
            if (status != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }
            if (developerId != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("developer").get("id"), developerId));
            }
            if (cityId != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("locality").get("city").get("id"), cityId));
            }
            if (localityId != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("locality").get("id"), localityId));
            }
            if (name != null) 
            {
                predicate = cb.and(predicate, cb.like(root.get("projectName"), "%" + name + "%"));
            }
            if (type != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("configurationsType").get("propertyType"), type));
            }
            if (configurations != null && !configurations.isEmpty()) 
            {
                Predicate configPredicate = cb.disjunction();
                for (String config : configurations) 
                {
                    configPredicate = cb.or(configPredicate, 
                    cb.like(cb.lower(cb.trim
                    (root.get("projectConfigurations"))), "%" + config.toLowerCase().trim() + "%"));
                }
                predicate = cb.and(predicate, configPredicate);
            }
            return predicate;
        };
    }

    public List<Project> setFloorPlanInList(List<Project> projects)
    {
        for (Project project : projects) 
        {
            List<Floorplan> floorplans = this.floorPlanRepository.findByProject(project);
            if(floorplans != null && !floorplans.isEmpty()) project.setFloorplans(floorplans); 

            List<PaymentPlan> paymentPlans = this.paymentPlanRepository.findByProject(project);
            if(paymentPlans != null && !paymentPlans.isEmpty()) project.setPaymentPlans(paymentPlans);

            List<Faq> faqs = this.faqRepository.findByProject(project);
            if(faqs != null && !faqs.isEmpty()) project.setFaqs(faqs);
        }
        return projects;
    }

    public Project setFloorPlan(Project project)
    {
        List<Floorplan> floorplans = this.floorPlanRepository.findByProject(project);
        if(floorplans != null && !floorplans.isEmpty()) project.setFloorplans(floorplans);

        List<PaymentPlan> paymentPlans = this.paymentPlanRepository.findByProject(project);
        if(paymentPlans != null && !paymentPlans.isEmpty()) project.setPaymentPlans(paymentPlans);

        List<Faq> faqs = this.faqRepository.findByProject(project);
        if(faqs != null && !faqs.isEmpty()) project.setFaqs(faqs);
        return project;
    }
    
}
