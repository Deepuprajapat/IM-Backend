package com.realestate.invest.Service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.realestate.invest.DTOModel.FloorPlanDTO;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.Model.Floorplan;

@Component
public interface FloorPlanService 
{

    Floorplan saveNewFloorplanByProject(Long projectId, FloorPlanDTO floorPlanDTO) throws Exception;

    Floorplan getById(Long Id) throws Exception;

    Floorplan updateById(Long Id, FloorPlanDTO floorPlanDTO) throws Exception;

    String deleteFloorPlanById(Long Id) throws Exception;

    List<Floorplan> getAllFloorplans(Long projectId, Long configurationId, Long configTypeId, PropertyType propertyType) throws Exception;

    List<Floorplan> findByProject(Long projectId) throws Exception;
    
}
