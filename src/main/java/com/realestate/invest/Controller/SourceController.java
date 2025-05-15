package com.realestate.invest.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.Source;
import com.realestate.invest.Service.SourceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/source")
public class SourceController 
{

    @Autowired
    private SourceService sourceService;
    
    
    @PostMapping("/save/new")
    public ResponseEntity<?> saveNewSource(@RequestBody Source source)
    {
        try
        {
            Source sources = this.sourceService.saveNewSource(source);
            return new ResponseEntity<>(sources, HttpStatus.CREATED);
        }
        catch(Exception ex)
        {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/id/{Id}")
    public ResponseEntity<?> findById(@PathVariable Long Id)
    {
        try
        {
            Source sources = this.sourceService.findById(Id);
            return new ResponseEntity<>(sources, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name)
    {
        try
        {
            List<Source> sources = this.sourceService.findByName(name);
            return new ResponseEntity<>(sources, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/by/id/{Id}")
    public ResponseEntity<?> updateSourceById(@PathVariable Long Id, @RequestBody Source source)
    {
        try
        {
            Source sources = this.sourceService.updateSourceById(Id, source);
            return new ResponseEntity<>(sources, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/by/id/{Id}")
    public ResponseEntity<?> deleteSourceById(@PathVariable Long Id)
    {
        try
        {
            String message = this.sourceService.deleteSourceById(Id);
            SuccessResponse successResponse = new SuccessResponse(message);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllSources()
    {
        try
        {
            List<Source> sources = this.sourceService.getAllSources();
            return new ResponseEntity<>(sources, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
