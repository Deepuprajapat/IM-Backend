package com.realestate.invest.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.realestate.invest.ExceptionHandler.ErrorResponse;
import com.realestate.invest.ExceptionHandler.SuccessResponse;
import com.realestate.invest.Model.StaticSiteData;
import com.realestate.invest.Service.StaticSiteDataService;

@CrossOrigin("*")
@RestController
@RequestMapping("/static-site-data")
public class StaticSiteDataController 
{
    
    @Autowired
    private StaticSiteDataService sDataService;

    /**
     * @Endpoint for saving new static site data associated with a specific user.
     *
     * @param siteData The static site data to be saved.
     * @param userId  The unique identifier of the user associated with the data.
     * @return ResponseEntity containing the saved static site data or an error response.
     */
    @PostMapping("/save/new/by/user/{userId}")
    public ResponseEntity<?> saveNewStaticSiteData(@RequestBody StaticSiteData siteData, @PathVariable Long userId)
    {
        try
        {
            StaticSiteData sData = this.sDataService.saveNewStaticSiteData(siteData, userId);
            return new ResponseEntity<>(sData, HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Endpoint for updating static site data identified by a specific ID.
     *
     * @param siteData The static site data to be updated.
     * @param id       The unique identifier of the static site data to be updated.
     * @return ResponseEntity containing the updated static site data or an error response.
     */
    @PutMapping("/update/by/id/{id}")
    public ResponseEntity<?> updateStaticSiteDataById(@RequestBody StaticSiteData siteData, @PathVariable Long id)
    {
        try
        {
            StaticSiteData sData = this.sDataService.updateStaticSiteDataById(siteData, id);
            return new ResponseEntity<>(sData, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Endpoint for retrieving all refund policies.
     *
     * @return ResponseEntity containing a list of static site data representing refund policies or an error response.
     */
    @GetMapping("/get/refund/policies")
    public ResponseEntity<?> findAllRefundPolicies()
    {
        try
        {
            StaticSiteData sData = this.sDataService.findAllRefundPolicies();
            return new ResponseEntity<>(sData, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Endpoint for retrieving all terms of services.
     *
     * @return ResponseEntity containing a list of static site data representing terms of services or an error response.
     */
    @GetMapping("/get/term/and/condition")
    public ResponseEntity<?> findAllTermsOfServices()
    {
        try
        {
            StaticSiteData sData = this.sDataService.findAllTermsOfServices();
            return new ResponseEntity<>(sData, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Endpoint for retrieving all privacy policies.
     *
     * @return ResponseEntity containing a list of static site data representing privacy policies or an error response.
     */
    @GetMapping("/get/privacy/policy")
    public ResponseEntity<?> findAllPrivacyPolicies()
    {
        try
        {
            StaticSiteData sData = this.sDataService.findAllPrivacyPolicies();
            return new ResponseEntity<>(sData, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Endpoint for retrieving information about the organization or entity described in the "About Us" section.
     *
     * @return ResponseEntity containing a list of static site data representing information about the organization or entity or an error response.
     */
    @GetMapping("/get/about/us/info")
    public ResponseEntity<?> findAboutUs()
    {
        try
        {
            StaticSiteData sData = this.sDataService.findAboutUs();
            return new ResponseEntity<>(sData, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Endpoint for retrieving all static site data.
     *
     * @return ResponseEntity containing a list of all static site data or an error response.
     */
    @GetMapping("/get/all")
    public ResponseEntity<?> getAllStaticSiteDatas()
    {
        try
        {
            List<StaticSiteData> sData = this.sDataService.getAllStaticSiteDatas();
            return new ResponseEntity<>(sData, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Endpoint for retrieving static site data by its unique identifier.
     *
     * @param dataId The unique identifier of the static site data to be retrieved.
     * @return ResponseEntity containing the static site data identified by the given ID or an error response.
     */
    @GetMapping("/getby/id/{dataId}")
    public ResponseEntity<?> getStaticSiteDataById(@PathVariable Long dataId)
    {
        try
        {
            StaticSiteData sData = this.sDataService.getStaticSiteDataById(dataId);
            return new ResponseEntity<>(sData, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Endpoint for deleting static site data by its unique identifier.
     *
     * @param dataId The unique identifier of the static site data to be deleted.
     * @return ResponseEntity indicating success or an error response.
     */
    @DeleteMapping("/deleteby/id/{dataId}")
    public ResponseEntity<?> deleteStaticSiteDataById(@PathVariable Long dataId)
    {
        try
        {
            this.sDataService.deleteStaticSiteDataById(dataId);
            SuccessResponse successResponse = new SuccessResponse("Data deleted successfully");
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @Endpoint for retrieving static site data associated with a specific user.
     *
     * @param userId The unique identifier of the user for whom static site data is retrieved.
     * @return ResponseEntity containing a list of static site data associated with the user or an error response.
     */
    @GetMapping("/getby/user/id/{userId}")
    public ResponseEntity<?> getStaticSiteDataByUserId(@PathVariable Long userId)
    {
        try
        {
            List<StaticSiteData> sData = this.sDataService.getStaticSiteDataByUserId(userId);
            return new ResponseEntity<>(sData, HttpStatus.OK);
        }
        catch(Exception e)
        {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "400", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
