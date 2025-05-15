package com.realestate.invest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.realestate.invest.DTOModel.PropertyDTO;
import com.realestate.invest.EnumAndJsons.PropertyType;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.Property;
import com.realestate.invest.Service.PropertyService;

@CrossOrigin("*")
@RestController
@RequestMapping("/property")
public class PropertyController 
{
    @Autowired
    private PropertyService propertyService;

    @PostMapping("/save/new")
    public ResponseEntity<?> saveNewProperty(@RequestBody PropertyDTO propertyDTO)
    {
        try
        {
            Property property = this.propertyService.saveNewProperty(propertyDTO);
            return new ResponseEntity<>(property, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/{Id}")
    public ResponseEntity<?> getById(@PathVariable Long Id)
    {
        try
        {
            Property property = this.propertyService.getById(Id);
            return new ResponseEntity<>(property, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/by/{Id}")
    public ResponseEntity<?> updateById(@PathVariable Long Id, @RequestBody PropertyDTO propertyDTO)
    {
        try
        {
            Property property = this.propertyService.updateById(Id, propertyDTO);
            return new ResponseEntity<>(property, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/by/{Id}")
    public ResponseEntity<?> deleteById(@PathVariable Long Id)
    {
        try
        {
            SuccessResponse successResponse = new SuccessResponse(this.propertyService.deleteById(Id));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> findAll(@RequestParam(required = false) Long cityId,
    @RequestParam(required = false) PropertyType propertyType,
    @RequestParam(required = false) String configTypeName,
    @RequestParam(required = false) String configurationName,
    @RequestParam(required = false) Long startDate, 
    @RequestParam(required = false) Long endDate, 
    @RequestParam(required =  false) Long userId, 
    @RequestParam(required = false) String name, 
    @RequestParam(required = false) Long projectId,
    @RequestParam(required = false) Long localityId,
    @RequestParam(required = false, defaultValue = "false") Boolean isDeleted, 
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "12") int size)
    {
        try
        {
            Page<Property> properties = this.propertyService.findAll(cityId, propertyType, configTypeName, configurationName, startDate, endDate, 
            userId, name, projectId, localityId, isDeleted, 
            PageRequest.of(page, size));
            return new ResponseEntity<>(properties, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/url/{url}")
    public ResponseEntity<?> findByUrl(@PathVariable String url)
    {
        try
        {
            Property property = this.propertyService.findByUrl(url);
            return new ResponseEntity<>(property, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/user/{userId}")
    public ResponseEntity<?> findByUser(@PathVariable Long userId, @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "12") int size)
    {
        try
        {
            Page<Property> properties = this.propertyService.findByUser(userId, PageRequest.of(page, size));
            return new ResponseEntity<>(properties, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/project/{projectId}")
    public ResponseEntity<?> findByProject(Long projectId, @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "12") int size)
    {
        try
        {
            Page<Property> properties = this.propertyService.findByProject(projectId, PageRequest.of(page, size));
            return new ResponseEntity<>(properties, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
}
