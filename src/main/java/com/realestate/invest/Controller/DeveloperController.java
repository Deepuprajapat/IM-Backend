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
import com.realestate.invest.DTOModel.DeveloperDTO;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.Developer;
import com.realestate.invest.Service.DeveloperService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/developer")
public class DeveloperController 
{

    @Autowired
    private DeveloperService developerService;

    @PostMapping("/save/new/by/user/{userId}")
    public ResponseEntity<?> saveNewDeveloperByUser(@PathVariable Long userId, @RequestBody DeveloperDTO developerDTO) 
    {
        try 
        {
            Developer developer = this.developerService.saveNewDeveloperByUser(userId, developerDTO);
            return new ResponseEntity<>(developer, HttpStatus.CREATED);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/id/{Id}")
    public ResponseEntity<?> getDeveloperById(@PathVariable Long Id) 
    {
        try 
        {
            Developer developer = this.developerService.getById(Id);
            return new ResponseEntity<>(developer, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/by/id/{Id}")
    public ResponseEntity<?> updateDeveloperById(@PathVariable Long Id, @RequestBody DeveloperDTO developerDTO)
    {
        try 
        {
            Developer developer = this.developerService.updateDeveloperById(Id, developerDTO);
            return new ResponseEntity<>(developer, HttpStatus.OK);
        } catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/by/id/{Id}")
    public ResponseEntity<?> deleteDeveloperById(@PathVariable Long Id) 
    {
        try 
        {
            SuccessResponse successResponse = new SuccessResponse(this.developerService.deleteDeveloperById(Id));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllDevelopers(
    @RequestParam(required = false) Boolean isVerifed, 
    @RequestParam(required = false) Boolean isActive,
    @RequestParam(required = false) String name) 
    {
        try 
        {
            List<Developer> developers = this.developerService.getAllDevelopers(isVerifed, isActive, name);
            return new ResponseEntity<>(developers, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/url/{url}")
    public ResponseEntity<?> getDeveloperByUrl(@PathVariable String url) 
    {
        try 
        {
            Developer developer = this.developerService.findByurl(url);
            return new ResponseEntity<>(developer, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
