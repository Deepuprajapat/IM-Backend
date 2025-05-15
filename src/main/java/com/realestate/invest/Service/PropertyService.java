package com.realestate.invest.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.realestate.invest.DTOModel.PropertyDTO;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.Model.Property;

@Component
public interface PropertyService 
{

    Property saveNewProperty(PropertyDTO propertyDTO) throws Exception;

    Property getById(Long Id) throws Exception;

    Property updateById(Long Id, PropertyDTO propertyDTO) throws Exception;

    String deleteById(Long Id) throws Exception;

    Page<Property> findAll(Long cityId, PropertyType propertyType, String configTypeName, String configurationName, Long startDate, Long endDate, 
    Long userId, String name, Long projectId, Long localityId, Boolean isDeleted, Pageable pageable) throws Exception;

    Property findByUrl(String url) throws Exception;

    Page<Property> findByUser(Long userId, Pageable pageable) throws Exception;

    Page<Property> findByProject(Long projectId, Pageable pageable) throws Exception;
    
}
