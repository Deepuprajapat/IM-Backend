package com.realestate.invest.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.DTOModel.FloorPlanDTO;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.Floorplan;
import com.realestate.invest.Service.FloorPlanService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/floor-plan")
public class FloorPlanController 
{
    
    @Autowired
    private FloorPlanService floorPlanService;

    @PostMapping("/save/new/by/project/{projectId}")
    public ResponseEntity<?> saveNewFloorplanByProject(@PathVariable Long projectId, @RequestBody FloorPlanDTO floorPlanDTO)
    {
        try
        {
            Floorplan floorplan = this.floorPlanService.saveNewFloorplanByProject(projectId, floorPlanDTO);
            return new ResponseEntity<>(floorplan, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/id/{Id}")
    public ResponseEntity<?> getById(@PathVariable Long Id)
    {
        try
        {
            Floorplan floorplan = this.floorPlanService.getById(Id);
            return new ResponseEntity<>(floorplan, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/by/id/{Id}")
    public ResponseEntity<?> updateById(@PathVariable Long Id, @RequestBody FloorPlanDTO floorPlanDTO)
    {
        try
        {
            Floorplan floorplan = this.floorPlanService.updateById(Id, floorPlanDTO);
            return new ResponseEntity<>(floorplan, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/by/id/{Id}")
    public ResponseEntity<?> deleteFloorPlanById(@PathVariable Long Id)
    {
        try
        {
            SuccessResponse successResponse = new SuccessResponse(this.floorPlanService.deleteFloorPlanById(Id));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllFloorplans(@RequestParam(required = false) Long projectId, 
    @RequestParam(required = false) Long configurationId, 
    @RequestParam(required = false) Long configTypeId, 
    @RequestParam(required = false) PropertyType propertyType)
    {
        try
        {
            List<Floorplan> floorplans = this.floorPlanService.getAllFloorplans(projectId, configurationId, configTypeId, propertyType);
            return new ResponseEntity<>(floorplans, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/project/{projectId}")
    public ResponseEntity<?> findByProject(@PathVariable Long projectId)
    {
        try
        {
            List<Floorplan> floorplans = this.floorPlanService.findByProject(projectId);
            return new ResponseEntity<>(floorplans, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
}
