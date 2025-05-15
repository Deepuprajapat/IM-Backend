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
import com.realestate.invest.DTOModel.LeadsDTO;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.Leads;
import com.realestate.invest.Service.LeadsService;

@CrossOrigin("*")
@RestController
@RequestMapping("/leads")
public class LeadsController 
{
    @Autowired
    private LeadsService leadsService;
    
    @PostMapping("/save/new/and/send-otp")
    public ResponseEntity<?> saveNewLeads(@RequestBody LeadsDTO leadsDTO)
    {
        try
        {
            this.leadsService.saveNewLeads(leadsDTO);
            SuccessResponse successResponse = new SuccessResponse("Leads Saved Successfully");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception e)
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
            Leads leads = this.leadsService.getById(Id);
            return new ResponseEntity<>(leads, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/by/{Id}")
    public ResponseEntity<?> updateLeadsById(@PathVariable Long Id, @RequestBody LeadsDTO leadsDTO)
    {
        try
        {
            Leads leads = this.leadsService.updateLeadsById(Id, leadsDTO);
            return new ResponseEntity<>(leads, HttpStatus.OK);
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
            SuccessResponse successResponse = new SuccessResponse(this.leadsService.deleteById(Id));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/get/all")
    public ResponseEntity<?> getAll(@RequestParam(required = false) String project, 
    @RequestParam(required = false) String phone, 
    @RequestParam(required = false) Long startDate, 
    @RequestParam(required = false) Long endDate,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "12") int size)
    {
        try
        {   
            Page<Leads> leads = this.leadsService.getAll(project, phone, startDate, endDate, PageRequest.of(page, size));
            return new ResponseEntity<>(leads, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/by/phone/{phone}")
    public ResponseEntity<?> findByPhone(@PathVariable String phone)
    {
        try
        {
            Leads leads = this.leadsService.findByPhone(phone);
            return new ResponseEntity<>(leads, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/validate/otp")
    public ResponseEntity<?> validateOTP(@RequestParam String phone, @RequestParam String OTP)
    {
        try
        {
            SuccessResponse successResponse = new SuccessResponse(this.leadsService.validateOTP(phone, OTP));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/resend/otp")
    public ResponseEntity<?> resendOTP(@RequestParam String phone)
    {
        try
        {
            SuccessResponse successResponse = new SuccessResponse(this.leadsService.resendOTP(phone));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/save/new")
    public ResponseEntity<?> saveNewLeadsWithoutOTP(@RequestBody LeadsDTO leadsDTO)
    {
        try
        {
            this.leadsService.saveNewLeadsWithoutOTP(leadsDTO);
            SuccessResponse successResponse = new SuccessResponse("Leads Saved Successfully");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/get/by/user")
    public ResponseEntity<?> getAllLeadsByUser(@RequestParam(required = false) String search,
    @RequestParam(required = false) Long startDate,
    @RequestParam(required = false) Long endDate,
    @RequestParam(defaultValue = "0") int page, 
    @RequestParam(defaultValue = "10") int size) 
    {
        try
        {
            Page<Leads> leads = this.leadsService.getAllLeadsByUser(search, startDate, endDate, PageRequest.of(page, size));
            return new ResponseEntity<>(leads, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
