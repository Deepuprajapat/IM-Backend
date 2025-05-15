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
import com.realestate.invest.Model.City;
import com.realestate.invest.Service.CityService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/city")
public class CityController 
{

    @Autowired
    private CityService cityService;

    @PostMapping("/save/new")
    public ResponseEntity<?> saveNewCity(@RequestBody City cityDTO) 
    {
        try 
        {
            City city = this.cityService.saveNewCity(cityDTO);
            return new ResponseEntity<>(city, HttpStatus.CREATED);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/id/{Id}")
    public ResponseEntity<?> findCityById(@PathVariable Long Id) 
    {
        try 
        {
            City city = this.cityService.findById(Id);
            return new ResponseEntity<>(city, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/by/id/{Id}")
    public ResponseEntity<?> updateCityById(@PathVariable Long Id, @RequestBody City cityDTO) 
    {
        try 
        {
            City city = this.cityService.updateCityById(Id, cityDTO);
            return new ResponseEntity<>(city, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/by/id/{Id}")
    public ResponseEntity<?> deleteCityById(@PathVariable Long Id) 
    {
        try 
        {
            SuccessResponse successResponse = new SuccessResponse(this.cityService.deleteCityById(Id));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllCities(@RequestParam(required = false) String stateName, 
    @RequestParam(required = false) String cityName, 
    @RequestParam(required = false, defaultValue = "true") Boolean isActive) 
    {
        try 
        {
            List<City> cities = this.cityService.getAllCity(stateName, cityName, isActive);
            return new ResponseEntity<>(cities, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/url/{url}")
    public ResponseEntity<?> findCityById(@PathVariable String url) 
    {
        try 
        {
            City city = this.cityService.findByUrl(url);
            return new ResponseEntity<>(city, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    
}
