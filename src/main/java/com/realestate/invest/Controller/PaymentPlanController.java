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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.PaymentPlan;
import com.realestate.invest.Service.PaymentPlanService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/payment-plan")
public class PaymentPlanController 
{

    @Autowired
    private PaymentPlanService paymentPlanService;

    @PostMapping("/save/new")
    public ResponseEntity<?> saveNewByProject(@RequestBody PaymentPlan paymentPlanDTO)
    {
        try
        {
            PaymentPlan paymentPlan = this.paymentPlanService.saveNewByProject(paymentPlanDTO);
            return new ResponseEntity<>(paymentPlan, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save/in/list/by/project/{projectId}")
    public ResponseEntity<?> savePaymentPlansByProjectId(@PathVariable Long projectId, @RequestBody List<PaymentPlan> paymentPlanDTOs)
    {
        try
        {
            List<PaymentPlan> paymentPlan = this.paymentPlanService.savePaymentPlansByProjectId(projectId, paymentPlanDTOs);
            return new ResponseEntity<>(paymentPlan, HttpStatus.OK);
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
            PaymentPlan paymentPlan = this.paymentPlanService.getById(id);
            return new ResponseEntity<>(paymentPlan, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/by/id/{id}")
    public ResponseEntity<?> updateById(@RequestBody PaymentPlan paymentPlanDTO)
    {
        try
        {
            PaymentPlan paymentPlan = this.paymentPlanService.updateById(paymentPlanDTO);
            return new ResponseEntity<>(paymentPlan, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/in/list")
    public ResponseEntity<?> updatePaymentPlansByProjectId(@RequestBody List<PaymentPlan> paymentPlanDTOs)
    {
        try
        {
            List<PaymentPlan> paymentPlan = this.paymentPlanService.updatePaymentPlansByProjectId(paymentPlanDTOs);
            return new ResponseEntity<>(paymentPlan, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/by/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id)
    {
        try
        {
            SuccessResponse successResponse = new SuccessResponse(this.paymentPlanService.deleteById(id));
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAll(@RequestParam(required = false) Long projectId)
    {
        try
        {
            List<PaymentPlan> paymentPlan = this.paymentPlanService.getAll(projectId);
            return new ResponseEntity<>(paymentPlan, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
}
