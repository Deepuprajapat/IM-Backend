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
import com.realestate.invest.Model.Locality;
import com.realestate.invest.Service.LocalityService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/locality")
public class LocalityController 
{

    @Autowired
    private LocalityService localityService;

    @PostMapping("/save/new/by/city/{cityId}")
    public ResponseEntity<?> saveNewLocality(@PathVariable Long cityId, @RequestBody Locality localityDTO)
    {
        try 
        {
            Locality locality = this.localityService.saveNewLocality(cityId, localityDTO);
            return new ResponseEntity<>(locality, HttpStatus.CREATED);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/id/{Id}")
    public ResponseEntity<?> getLocalityById(@PathVariable Long Id) 
    {
        try 
        {
            Locality locality = this.localityService.findById(Id);
            return new ResponseEntity<>(locality, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/by/id/{Id}/city/{cityId}")
    public ResponseEntity<?> updateLocalityById(@PathVariable Long Id, @PathVariable Long cityId, @RequestBody Locality localityDTO) 
    {
        try 
        {
            Locality locality = this.localityService.updateLocalityById(Id, cityId, localityDTO);
            return new ResponseEntity<>(locality, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/by/id/{Id}")
    public ResponseEntity<?> deleteLocalityById(@PathVariable Long Id) 
    {
        try 
        {
            SuccessResponse successResponse = new SuccessResponse(this.localityService.deleteLocalityById(Id));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllLocalities(@RequestParam(required = false) Long cityId, 
    @RequestParam(required = false) String cityName, 
    @RequestParam(required = false, defaultValue = "true") Boolean isActive) 
    {
        try 
        {
            List<Locality> localities = this.localityService.getAllLocalities(cityId, cityName, isActive);
            return new ResponseEntity<>(localities, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/url/{url}")
    public ResponseEntity<?> getLocalityByUrl(@PathVariable String url) 
    {
        try 
        {
            Locality locality = this.localityService.findByUrl(url);
            return new ResponseEntity<>(locality, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    
}
