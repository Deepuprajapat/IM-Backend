package com.realestate.invest.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.ProjectConfigurationType;
import com.realestate.invest.Service.ProjectConfigurationTypeService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/project-configuration-type")
public class ProjectConfigurationTypeController 
{
    
    @Autowired
    private ProjectConfigurationTypeService pConfigService;

    @PostMapping("/save/new")
    public ResponseEntity<?> saveNewPropertyConfigurationType(@RequestBody ProjectConfigurationType pctDto)
    {
        try
        {
            ProjectConfigurationType pcType = this.pConfigService.saveNewPropertyConfigurationType(pctDto);
            return new ResponseEntity<>(pcType, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/id/{Id}")
    public ResponseEntity<?> findById(@PathVariable Long Id)
    {
        try
        {
            ProjectConfigurationType pcType = this.pConfigService.findById(Id);
            return new ResponseEntity<>(pcType, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/by/id/{Id}")
    public ResponseEntity<?> updatePropertyConfigurationTypeById(@PathVariable Long Id, @RequestBody ProjectConfigurationType pctDto)
    {
        try
        {
            ProjectConfigurationType pcType = this.pConfigService.updatePropertyConfigurationTypeById(Id, pctDto);
            return new ResponseEntity<>(pcType, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/by/id/{Id}")
    public ResponseEntity<?> deletePropertyConfigurationTypeById(@PathVariable Long Id)
    {
        try
        {
            SuccessResponse successResponse = new SuccessResponse(this.pConfigService.deletePropertyConfigurationTypeById(Id));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllPropertyConfigurationType(@RequestParam(required = false) PropertyType propertyType)
    {
        try
        {
            List<ProjectConfigurationType> pcType = this.pConfigService.getAllPropertyConfigurationType(propertyType);
            return new ResponseEntity<>(pcType, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    

}
