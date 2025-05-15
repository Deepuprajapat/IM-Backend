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
import com.realestate.invest.Model.ReraInfo;
import com.realestate.invest.Service.ReraInfoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rera-info")
public class ReraInfoController 
{

    @Autowired
    private ReraInfoService reraInfoService;
    
    @PostMapping("/save/new")
    public ResponseEntity<?> saveNew(@RequestBody ReraInfo reraDTO)
    {
        try
        {
            ReraInfo reraInfo = this.reraInfoService.saveNew(reraDTO);
            return new ResponseEntity<>(reraInfo, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/{Id}")
    public ResponseEntity<?> findById(@PathVariable Long Id)
    {
        try
        {
            ReraInfo reraInfo = this.reraInfoService.findById(Id);
            return new ResponseEntity<>(reraInfo, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/by/{Id}")
    public ResponseEntity<?> updateById(@PathVariable Long Id, @RequestBody ReraInfo reraDTO)
    {
        try
        {
            ReraInfo reraInfo = this.reraInfoService.updateById(Id, reraDTO);
            return new ResponseEntity<>(reraInfo, HttpStatus.OK);
        }
        catch(Exception e)
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
            SuccessResponse successResponse = new SuccessResponse(this.reraInfoService.deleteById(Id));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAll(@RequestParam(required = false) Long projectId, 
    @RequestParam(required = false) Long startDate, 
    @RequestParam(required = false) Long endDate)
    {
        try
        {
            List<ReraInfo> reraInfo = this.reraInfoService.getAll(projectId, startDate, endDate);
            return new ResponseEntity<>(reraInfo, HttpStatus.OK);
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
            List<ReraInfo> reraInfo = this.reraInfoService.findByProject(projectId);
            return new ResponseEntity<>(reraInfo, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
}
