package com.realestate.invest.Utils;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.Model.Property;
import jakarta.persistence.criteria.Predicate;

@Component
public class PropertyUtility 
{
    public static Specification<Property> filterByCriteria(Long cityId, PropertyType propertyType, String configTypeName, String configurationName, Long startDate, Long endDate, Long userId, String name,
    Long projectId, Long localityId, Boolean isDeleted) 
    {
        return (root, query, cb) -> 
        {
            Predicate predicate = cb.conjunction();

            if (propertyType != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("configuration")
                        .get("configurationType").get("propertyType"), propertyType));
            }
            if (configTypeName != null && !configTypeName.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("configuration")
                        .get("configurationType").get("configurationTypeName")), "%" + configTypeName.toLowerCase() + "%"));
            }
            if (configurationName != null && !configurationName.isEmpty()) 
            {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("configuration")
                        .get("projectConfigurationName")), "%" + configurationName.toLowerCase() + "%"));
            }
            if (startDate != null) 
            {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("createdDate"), startDate));
            }
            if (endDate != null) 
            {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("createdDate"), endDate));
            }
            if (userId != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("user").get("id"), userId));
            }
            if (name != null && !name.isEmpty()) 
            {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("propertyName")), "%" + name.toLowerCase() + "%"));
            }
            if (projectId != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("project").get("id"), projectId));
            }
            if (localityId != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("locality").get("id"), localityId.intValue()));
            }
            if (cityId != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("locality").get("city").get("id"), cityId));
            }
            if (isDeleted != null) 
            {
                predicate = cb.and(predicate, cb.equal(root.get("isDeleted"), isDeleted));
            }
            return predicate;
        };
    }
    
}
