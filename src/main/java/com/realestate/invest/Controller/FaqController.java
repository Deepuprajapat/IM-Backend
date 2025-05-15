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
import com.realestate.invest.Model.Faq;
import com.realestate.invest.Service.FaqService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/faqs")
public class FaqController 
{

    @Autowired
    private FaqService faqService;

    
    @PostMapping("/save/new")
    public ResponseEntity<?> saveNewByProject(@RequestBody Faq faqDTO)
    {
        try
        {
            Faq faq = this.faqService.saveNewByProject(faqDTO);
            return new ResponseEntity<>(faq, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save/in/list/by/project/{projectId}")
    public ResponseEntity<?> saveFaqsByProjectId(@PathVariable Long projectId, @RequestBody List<Faq> faqDTOs)
    {
        try
        {
            List<Faq> faq = this.faqService.saveFaqsByProjectId(projectId, faqDTOs);
            return new ResponseEntity<>(faq, HttpStatus.CREATED);
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
            Faq faq = this.faqService.getById(Id);
            return new ResponseEntity<>(faq, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/by/{Id}")
    public ResponseEntity<?> updateById(@RequestBody Faq faqDTO)
    {
        try
        {
            Faq faq = this.faqService.updateById(faqDTO);
            return new ResponseEntity<>(faq, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/in/list")
    public ResponseEntity<?> updateFaqsByProjectId(@RequestBody List<Faq> faqDTOs)
    {
        try
        {
            List<Faq> faq = this.faqService.updateFaqsByProjectId(faqDTOs);
            return new ResponseEntity<>(faq, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/by/{Id}")
    public ResponseEntity<?> deleteById(@PathVariable Long Id)
    {
        try
        {
            SuccessResponse successResponse = new SuccessResponse(this.faqService.deleteById(Id));
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
            List<Faq> faq = this.faqService.getAll(projectId);
            return new ResponseEntity<>(faq, HttpStatus.OK);
        }
        catch (Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
}
