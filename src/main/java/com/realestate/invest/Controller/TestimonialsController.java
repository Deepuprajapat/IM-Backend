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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.Testimonials;
import com.realestate.invest.Service.TestimonialsService;
import jakarta.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/testimonials")
public class TestimonialsController 
{

    @Autowired
    private TestimonialsService testimonialsService; 

    @PostMapping("/save/new")
    public ResponseEntity<?> saveNewTestimonials(@RequestBody Testimonials testimonialsDTO) 
    {
        try 
        {
            Testimonials testimonials = this.testimonialsService.saveNewTestimonials(testimonialsDTO);
            return new ResponseEntity<>(testimonials, HttpStatus.CREATED);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) 
    {
        try 
        {
            Testimonials testimonials = this.testimonialsService.getById(id);
            return new ResponseEntity<>(testimonials, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "404", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/by/id/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody Testimonials testimonialsDTO) 
    {
        try 
        {
            Testimonials updatedTestimonials = this.testimonialsService.updateById(id, testimonialsDTO);
            return new ResponseEntity<>(updatedTestimonials, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/by/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) 
    {
        try 
        {
            SuccessResponse successResponse = new SuccessResponse(this.testimonialsService.deleteById(id));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "404", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/approve/by/id/{id}")
    public ResponseEntity<?> approveById(@PathVariable Long id, @NotNull @RequestParam Boolean isApproved) 
    {
        try 
        {
            Testimonials approvedTestimonials = this.testimonialsService.approveById(id, isApproved);
            return new ResponseEntity<>(approvedTestimonials, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAll(@RequestParam(required = false) Boolean isApproved,
    @RequestParam(required = false) Long startDate,
    @RequestParam(required = false) Long endDate,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "12") int size)  
    {
        try 
        {
            Page<Testimonials> testimonialsPage = this.testimonialsService.getAll(isApproved, startDate, endDate, 
            PageRequest.of(page, size));
            return new ResponseEntity<>(testimonialsPage, HttpStatus.OK);
        } 
        catch (Exception e) 
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}

