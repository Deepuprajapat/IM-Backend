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
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.ProjectConfiguration;
import com.realestate.invest.Service.ProjectConfigurationService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/project-configuration")
public class ProjectConfigurationController 
{
    @Autowired
    private ProjectConfigurationService pConfigService;

    @PostMapping("/save/new/by/config-type/{configTypeId}")
    public ResponseEntity<?> saveNewProjectConfigurationByType(@PathVariable Long configTypeId, @RequestBody ProjectConfiguration pctDto)
    {
        try
        {
            ProjectConfiguration pcType = this.pConfigService.saveNewProjectConfigurationByType(configTypeId, pctDto);
            return new ResponseEntity<>(pcType, HttpStatus.CREATED);
        }
        catch (Exception e)
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
            ProjectConfiguration pcType = this.pConfigService.getById(Id);
            return new ResponseEntity<>(pcType, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/by/id/{Id}")
    public ResponseEntity<?> updateById(@PathVariable Long Id, @RequestParam(required = false) Long configTypeId, 
    @RequestBody ProjectConfiguration pctDto)
    {
        try
        {
            ProjectConfiguration pcType = this.pConfigService.updateById(Id, configTypeId, pctDto);
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
    public ResponseEntity<?> deleteById(@PathVariable Long Id)
    {
        try
        {
            SuccessResponse successResponse = new SuccessResponse(this.pConfigService.deleteById(Id));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAll(@RequestParam(required = false) Long configTypeId)
    {
        try
        {
            List<ProjectConfiguration> pcType = this.pConfigService.getAll(configTypeId);
            return new ResponseEntity<>(pcType, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
